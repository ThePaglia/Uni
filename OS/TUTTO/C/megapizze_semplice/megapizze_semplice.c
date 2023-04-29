/* file:  megapizze_semplice.c 
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

/* compilare con -D_BSD_SOURCE  per random */


#define NUMPERSONE 5
#define NUMPOSTI 4	/* posti a tavola */

/* stati dei tavoli */
#define TAVOLOLIBERO	0
#define	TAVOLOOCCUPATO	1
#define SIMANGIA	2	/* unico stato in cui sicuramente non possono sedersi altri clienti */

/* variabili per la sincronizzazione */
		typedef struct sharedBuffer {
		int		statotavolo;
		int		postioccupati;
		pthread_cond_t	condAttesaTavoloLibero;
		pthread_cond_t	condAttesaMangiare;
		pthread_mutex_t mutex; 
} SharedBuffer;
SharedBuffer *P;

/* funzione da chiamare detenendo la mutua esclusione */
void stampastatotavoli( void) {
	printf("statotavolo %d  postioccupati %d \n",
		P->statotavolo, P->postioccupati );
}

void Cliente (int indice) 
{ 
	char Clabel[128];

	sprintf(Clabel,"C%d",indice);

	while(1) {

		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock( &P->mutex, Clabel );

		stampastatotavoli();

		printf("cliente %s cerca tavolo con un posto libero\n", Clabel ); 
		fflush(stdout);

		/* if (	postioccupati<NUMPOSTI  &&  statotavolo != SIMANGIA ) */
		if (	P->postioccupati<NUMPOSTI  
					&&  
						(
							P->statotavolo == TAVOLOLIBERO
							||
							P->statotavolo == TAVOLOOCCUPATO
						)
		   )
		{
			printf("cliente %s trovato posto libero in tavolo \n", Clabel ); 
			fflush(stdout);

			/* mi aggiungo al tavolo */
		 	P->postioccupati++;

			/* se sono il primo arrivato occupo il tavolo */
			if( P->postioccupati == 1 ) {
				P->statotavolo=TAVOLOOCCUPATO;
				/* mi metto in attesa di mangiare */
				DBGpthread_cond_wait( &P->condAttesaMangiare, &P->mutex, Clabel );
			}

			/* se sono il quarto arrivato (tutti i posti occupati)
			   allora la pizza appare magicamente e
				 si deve cominciare a mangiare tutti
			*/
		 	else if ( P->postioccupati == NUMPOSTI ) {
				 P->statotavolo=SIMANGIA;
				 DBGpthread_cond_broadcast( &P->condAttesaMangiare, Clabel );
			}

			/* se invece manca qualcuno a tavola, si aspetta ancora */
			else {
				 DBGpthread_cond_wait( &P->condAttesaMangiare, &P->mutex, Clabel );
			}

			/* quando arrivo qui, posso mangiare */
			DBGpthread_mutex_unlock( &P->mutex, Clabel );

			printf("cliente %s mangia \n", Clabel ); 
			fflush(stdout);

			DBGsleep( 3 , Clabel );
	
			/* ora si deve uscire */	
			DBGpthread_mutex_lock( &P->mutex, Clabel );

			/* mi alzo dal tavolo */
			P->postioccupati--;
			/* se si sono alzati tutti ed io sono l'ultimo, 
			   libero il tavolo 
			   e risveglio chi aspettava
			*/
			if( P->postioccupati == 0 )  {
				P->statotavolo=TAVOLOLIBERO;
				DBGpthread_cond_broadcast( &P->condAttesaTavoloLibero, Clabel );
			}

			printf("cliente %s va via \n", Clabel ); 
			fflush(stdout);

			DBGpthread_mutex_unlock( &P->mutex, Clabel );
			
			/* cliente va in giro per 4 secondi */
			DBGsleep( 2+indice ,Clabel);

			/* torno al while */
		} 
		else {
			/* nessun posto libero, devo aspettare */

			printf("cliente %s deve aspettare \n", Clabel ); 
			fflush(stdout);

			DBGpthread_cond_wait( &P->condAttesaTavoloLibero, &P->mutex, Clabel );
			DBGpthread_mutex_unlock( &P->mutex, Clabel );
		}

	}
	exit(0); 
} 


int main (int argc, char* argv[] ) 
{ 
	int shmfd, rc, i;
	pid_t pid;
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

	rc = pthread_cond_init( &P->condAttesaTavoloLibero, &cvattr); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_cond_init( &P->condAttesaMangiare, &cvattr); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	P->statotavolo=TAVOLOLIBERO;
	P->postioccupati=0;

	/* creo clienti */
	for(i=0;i<(NUMPERSONE-1);i++) {
		pid=fork();
		if(pid<0) 
			PrintERROR_andExit(errno,"fork failed");
		else if(pid==0) {
			/* figlio */
			Cliente(i);
			exit(0);
		}
	}

	Cliente(NUMPERSONE-1);
	exit(0);

} 
  
  
