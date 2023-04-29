/* es21_memcondivisafiglio.c
   a causa dell'uso di strerror_r, usato in printerror.h,
   compilare con -D_POSIX_C_SOURCE >= 200112L
*/

/* messo prima perche' contiene define _POSIX_C_SOURCE */
#include "printerror.h"


#include <stdlib.h>
#include <unistd.h>   /* exit() etc */
#include <stdio.h>
#include <string.h>     /* per strerror_r  and  memset */
#include <sys/mman.h>  /* shm_* stuff, and mmap() */
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

#define BUFSIZE 512
#define SHMOBJ_PATH "/es21"

/* il padre aspetta la terminazione del figlio */
void dezombizzaFiglioEStampa(pid_t pid, char *buffer) {
    pid_t retpid; int status;
   
    /* attendo la terminazione di un solo figlio */ 
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

            if( rc==0 ) {
                printf("contenuto segmento: %s\n", buffer );
                fflush(stdout);
            }
            else {
                printf("figlio restituisce rc= %i\n", rc );
                fflush(stdout);
            }
        }
        else {
            printf("figlio termina in modo anormale\n" );
            fflush(stdout);
        }
    }
}

void eliminaSegmento( char *path, char *shared_seg, int shared_seg_size )
{
    if ( munmap( shared_seg, shared_seg_size ) != 0) {
        perror("munmap() failed");
        exit(1);
    }
    /* requesting the removal of the shm object */
    if (shm_unlink(path) != 0) {
        perror("shm_unlink() failed");
        exit(1);
    }
}

int main() {
    int shmfd, rc;
    pid_t pid;
    char *buffer;
    int shared_seg_size = BUFSIZE;

    shmfd = shm_open( SHMOBJ_PATH , O_CREAT | O_EXCL | O_RDWR, S_IRWXU );
#if 0
    shmfd = shm_open( SHMOBJ_PATH , O_CREAT /*| O_EXCL*/ | O_RDWR,         S_IRWXU );
#endif

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

    buffer = (char *)mmap(NULL, shared_seg_size ,
        PROT_READ|PROT_WRITE, MAP_SHARED, shmfd, 0);

    memset( buffer, (int)'B', BUFSIZE-1 );
    buffer[BUFSIZE-1]='\0';

    pid=fork();
    if(pid<0) { 
        int myerrno=errno;
        eliminaSegmento( SHMOBJ_PATH , buffer, shared_seg_size );
        PrintERROR_andExit( myerrno,"fork() failed ");
    }
    else if ( pid == 0) { /* figlio */
    	memset( buffer, (int)'K', BUFSIZE-1 );
        buffer[BUFSIZE-1]='\0';
        exit(0); 
    }
    else {  /* padre */

	dezombizzaFiglioEStampa(pid, buffer );
        eliminaSegmento( SHMOBJ_PATH , buffer, shared_seg_size );
    }
    return(0);
}


