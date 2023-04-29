/* acrossProcessBoundary.c  
   4-17 Synchronization Across Process Boundaries
   a causa dell'uso di strerror_r, usato in printerror.h,
   compilare con -D_POSIX_C_SOURCE >= 200112L
*/

/* messo prima perche' contiene define _POSIX_C_SOURCE */
#include "printerror.h"
#include "DBGpthread.h"


#include <stdlib.h>
#include <unistd.h>   /* exit() etc */
#include <stdio.h>
#include <string.h>     /* per strerror_r  and  memset */
#include <sys/mman.h>  /* Memory MANagement: shm_* stuff, and mmap() */
#include <sys/types.h>
#include <signal.h>
#include <sys/time.h>	/* timeval{} for select() */
#include <time.h>	/* timespec{} for pselect() */
#include <limits.h>	/* for OPEN_MAX */
#include <errno.h>
#include <sys/wait.h>
#include <assert.h>
#include <pthread.h>
#include <semaphore.h>


/*
#include <sys/ipc.h>
#include <sys/shm.h>
*/
#include <fcntl.h>
#include <sys/stat.h>

typedef struct { 
     char buf; 		/* buffer di scambio */
     int occupied; 
     pthread_mutex_t mutex; 
     pthread_cond_t cond_full; 
     pthread_cond_t cond_empty; } 
buffer_t; 


void producer_driver(buffer_t *b);
void consumer_driver(buffer_t *b);

void producer(buffer_t *b, char item)
{
    DBGpthread_mutex_lock(&b->mutex, "producer lock");
   
    while ( b->occupied >= 1 )
        DBGpthread_cond_wait(&b->cond_empty, &b->mutex, "producer wait");

    assert(b->occupied < 1);

    b->buf = item;

    b->occupied = 1;

	printf("producer puts %c\n", item );
	fflush(stdout);

    DBGpthread_cond_signal(&b->cond_full, "producer signal");
    DBGpthread_mutex_unlock(&b->mutex, "producer unlock");
}

char consumer(buffer_t *b)
{
    char item;

    DBGpthread_mutex_lock(&b->mutex ,"consumer lock");
    while(b->occupied <= 0)
        DBGpthread_cond_wait(&b->cond_full, &b->mutex , "consumer wait");

    assert(b->occupied > 0);

    item = b->buf;
    b->occupied=0;

	printf("consumer gets %c\n", item );
	fflush(stdout);


    DBGpthread_cond_signal(&b->cond_empty, "consumer signal");
    DBGpthread_mutex_unlock(&b->mutex, "consumer unlock");

    return(item);
}

/* il padre aspetta la terminazione del figlio */
void dezombizzaFiglio(pid_t pid) {
    pid_t retpid; int status;
    
    do {
        retpid = waitpid ( pid, &status, 0 );
    } while( (retpid<0) && (errno==EINTR) );
    if( retpid<0 ) {
        PrintErrnoAndExit ( "waitpid failed " );
        exit(3);
    }
    else {
        if(  WIFEXITED(status) ) { /* figlio terminato normalmente */
            int rc;
            rc =   WEXITSTATUS(status);
                 printf("figlio restituisce status %d\n", rc );
                 fflush(stdout);
        }
        else {
            printf("figlio termina in modo anormale\n" );
            fflush(stdout);
        }
    }
}


int main() {
    int shmfd, rc;
    pid_t pid;
    buffer_t *buffer;
    int shared_seg_size = sizeof(buffer_t);
    pthread_mutexattr_t mattr;
    pthread_condattr_t cvattr;

    /* shmfd = shm_open( "/pedala", O_CREAT | O_EXCL | O_RDWR, S_IRWXU ); */
    shmfd = shm_open( "/pedala", O_CREAT /*| O_EXCL*/ | O_RDWR,         S_IRWXU );
    if (shmfd < 0) {
        perror("In shm_open()");
        exit(1);
    }
    /* adjusting mapped file size (make room for the whole segment to map) */
    rc = ftruncate(shmfd, shared_seg_size);
    if (rc != 0) {
        perror("ftruncate() failed");
        exit(1);
    }

    buffer = (buffer_t *)mmap(NULL, sizeof(buffer_t),
        PROT_READ|PROT_WRITE, MAP_SHARED, shmfd, 0);
    buffer->occupied = 0;

    rc=pthread_mutexattr_init(&mattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_init  failed");
    rc=pthread_mutexattr_setpshared(&mattr,PTHREAD_PROCESS_SHARED);
    if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_setpshared  failed");
    rc=pthread_mutex_init(&buffer->mutex, &mattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init  failed");

    rc=pthread_condattr_init(&cvattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_condattr_init  failed");
    rc=pthread_condattr_setpshared(&cvattr,PTHREAD_PROCESS_SHARED);
    if( rc ) PrintERROR_andExit(rc,"pthread_condattr_setpshared  failed");

    rc=pthread_cond_init(&buffer->cond_empty, &cvattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_cond_init  failed");
    rc=pthread_cond_init(&buffer->cond_full, &cvattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_cond_init  failed");

    pid=fork();
    if(pid<0) { 
        PrintERROR_andExit(errno,"fork() failed ");
    }
    else if ( pid == 0) /* figlio */
        consumer_driver(buffer);
    else {  /* padre */
        producer_driver(buffer);
	/* sleep(15); */
	dezombizzaFiglio(pid);
	if (shm_unlink("/pedala") != 0) {
		perror("shm_unlink() failed");
		exit(1);
    	}
    }
    return(0);
}

void producer_driver(buffer_t *b) {
    int item;

    printf("digitare caratteri su tastiera\n");
    printf("digitare CTRL+D per terminare\n");
    fflush(stdout);

    while (1) {
        item = getchar();
        if (item == EOF) {
            producer(b, '\0');
            break;
        } else
            producer(b, (char)item);
    }
}

void consumer_driver(buffer_t *b) {
    char item; int ris;

    while (1) {
        if ((item = consumer(b)) == '\0')
            break;
        ris=putchar(item); /* stampo a video il carattere */
	if(ris==EOF) PrintERROR_andExit(errno,"putchar failed");
    }
}




