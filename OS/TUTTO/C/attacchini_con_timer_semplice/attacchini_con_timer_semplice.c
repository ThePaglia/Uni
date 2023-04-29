/* file:   attacchinicontimer_semplice.c
*/ 

/* la 	#define _POSIX_C_SOURCE 200112L   e' dentro printerror.h */


/* messo prima perche' contiene define _POSIX_C_SOURCE */
#include "printerror.h"

#include <unistd.h>   /* exit() etc */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>     /* per strerror_r  and  memset */
#include <sys/mman.h>  /* shm_* stuff, and mmap() */
#include <sys/types.h>
#include <signal.h>
#include <sys/time.h> /*gettimeofday() struct timeval timeval{} for select()*/
#include <time.h> /* timespec{} for pselect() */
#include <limits.h> /* for OPEN_MAX */
#include <errno.h>
#include <sys/wait.h>
#include <assert.h>
#include <semaphore.h>
#include <stdint.h>	/* uint64_t intptr_t */
#include <inttypes.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <pthread.h> 

#include "DBGpthread.h"

#define NUMATTACCHINI 2

typedef struct sharedBuffer {
	int numAttacchiniInAttesaDiLavorare;	/* num attacchini in attesa di lavorare */
	int OrologioFaiWait;
	pthread_mutex_t  mutex;
	pthread_cond_t condAttesaArrivoAttacchino2;	/* attesa per arrivo del secondo attacchino */
	pthread_cond_t condOrologioCominciaCountdown;	/* orologio attende di contare i secondi */
	pthread_cond_t condAttesaSuonoOrologio;	/* attesa per fine del lavoro */
} SharedBuffer;
SharedBuffer *P;

void Attacchino (int indice) 
{ 
	char Alabel[128];

	sprintf(Alabel,"A%i" , indice);

	while(1) {

		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock( &P->mutex, Alabel );
		P->numAttacchiniInAttesaDiLavorare++;

		printf("Attacchino %s vorrebbe lavorare\n", Alabel ); 
		fflush(stdout);

		/* se sono l'attacchino arrivato per primo, devo aspettare il secondo attacchino */
		if( P->numAttacchiniInAttesaDiLavorare==1)
			DBGpthread_cond_wait( &P->condAttesaArrivoAttacchino2, &P->mutex, Alabel);
		else {	/* numAttacchiniInAttesaDiLavorare==2 
			se sono l'attacchino arrivato per secondo, devo svegliare il primo 
			*/
			DBGpthread_cond_signal( &P->condAttesaArrivoAttacchino2, Alabel);
			/* e devo far partire il timer */
			P->OrologioFaiWait=0;
			DBGpthread_cond_signal( &P->condOrologioCominciaCountdown, Alabel);
		}
		P->numAttacchiniInAttesaDiLavorare--;


		printf("Attacchino %s comincia ad attaccare manifesti\n", Alabel ); 
		fflush(stdout);

		/* attendo il suono dell'orologio */
		DBGpthread_cond_wait( &P->condAttesaSuonoOrologio, &P->mutex, Alabel);

		/* rilascio mutua esclusione sulle variabili */
		DBGpthread_mutex_unlock( &P->mutex, Alabel);

		/* QUI L' ATTACCHINO VA A PRENDERE ALTRI MANIFESTI */
		printf("Attacchino %s prende altri manifesti e colla\n", Alabel ); 
		fflush(stdout);
	}
	exit(0); 
} 

void Orologio (void) 
{ 
	char Olabel[128];

	sprintf(Olabel,"Orologio");

	while(1) {
	  DBGpthread_mutex_lock(&P->mutex,Olabel); 

		if( P->OrologioFaiWait ) {
			DBGpthread_cond_wait( &P->condOrologioCominciaCountdown, &P->mutex, Olabel );
		}
		P->OrologioFaiWait=1;
	  DBGpthread_mutex_unlock(&P->mutex,Olabel); 

		sleep(5);

		printf("Orologio avvisa gli attacchini\n" ); 
		fflush(stdout);

		DBGpthread_cond_broadcast( &P->condAttesaSuonoOrologio, Olabel);
	}
	exit(0); 
} 

int main (int argc, char* argv[] ) 
{ 
	int shmfd, rc, i;
	pid_t pid[NUMATTACCHINI];
	int shared_seg_size = sizeof(SharedBuffer);
	pthread_mutexattr_t mattr;
	pthread_condattr_t cvattr;

	shmfd = shm_open( "/pedala", O_CREAT /*| O_EXCL*/ | O_RDWR, S_IRWXU );
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

	P = (SharedBuffer*)mmap(NULL, shared_seg_size,
		PROT_READ|PROT_WRITE, MAP_SHARED, shmfd, 0);
	if( P==MAP_FAILED )
	 		PrintERROR_andExit(errno,"mmap  failed");

	rc=pthread_mutexattr_init(&mattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_init  failed");
	rc=pthread_mutexattr_setpshared(&mattr,PTHREAD_PROCESS_SHARED);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_setpshared  failed");
	rc=pthread_condattr_init(&cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_condattr_init  failed");
	rc=pthread_condattr_setpshared(&cvattr,PTHREAD_PROCESS_SHARED);
	if( rc ) PrintERROR_andExit(rc,"pthread_condattr_setpshared  failed");

	rc = pthread_mutex_init(&P->mutex, &mattr ); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_cond_init(&P->condAttesaArrivoAttacchino2, &cvattr ); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condAttesaSuonoOrologio, &cvattr ); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condOrologioCominciaCountdown, &cvattr ); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	P->OrologioFaiWait=1; /* la prima volta l'orologio deve aspettare */
	P->numAttacchiniInAttesaDiLavorare=0;	/* all'inizio nessuno attacchino in attesa di lavorare */

	for(i=0;i<NUMATTACCHINI;i++) pid[i]=0;
	for(i=0;i<NUMATTACCHINI;i++) {
		pid[i]=fork();
		if(pid[i]<0) 
			PrintERROR_andExit(errno,"fork failed");
		else if(pid[i]==0) {
			/* figlio */
			Attacchino(i);
			exit(0);
		}
	}
	/* padre */
	Orologio();
	exit(0);
} 
  
