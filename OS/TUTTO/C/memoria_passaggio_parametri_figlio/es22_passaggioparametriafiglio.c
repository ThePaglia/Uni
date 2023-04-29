/* es22_passaggioparametriafiglio.c
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
void dezombizzaFiglioGenerico(vois) {
    pid_t retpid; int status;
   
    /* attendo la terminazione di un solo figlio */ 
    do {
        retpid = waitpid ( -1, &status, 0 );
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
    pid_t pid;
    int i=0;

    for( i=0; i<10; i++ ) {

        pid=fork();
        if(pid<0) { 
            PrintERROR_andExit( errno,"fork() failed ");
        }
        else if ( pid == 0) { /* figlio */
            printf("indice %i \n", i );
	    fflush(stdout);
            exit(0); 
        }
        else {  /* padre */
            ;
        }
    }
    for( i=0; i<10; i++ ) {
        dezombizzaFiglioGenerico();
    }
    return(0);
}


