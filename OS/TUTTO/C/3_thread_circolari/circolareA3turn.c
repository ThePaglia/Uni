/* file:  circolareA3turn.c 
   Routine che fornisce tre passaggi di testimone tra 3 processi
   il primo fa partire il secondo, il secondo fa partire il terzo
   e il terzo fa partire nuovamente il primo e cosi via
   Implementata con una sola condition variable.
*/ 

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#endif


#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>
#include <pthread.h> 
#include "printerror.h"
#include "DBGpthread.h"

#define NUMTHREADS 3

/* dati da proteggere */
uint64_t valGlobale=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   cond;
int turno;

/* variabili di debug, per verificare che
   la mutua esclusione e la sincronizzazione
   funzionino bene.
B
   Contano quante volte i due pthread
   accedono alla variabile globale da proteggere.
*/
uint64_t countPrimo=0, countSecondo=0, countTerzo=0;

void *Primo (void *arg) 
{ 
	while(1) {
		DBGpthread_mutex_lock(&mutex,"1"); 

		while( turno != 1  ) {
			DBGpthread_cond_wait(&cond,&mutex,"1");
			if( turno != 1 ) 
				DBGpthread_cond_signal(&cond,"1");
		}

		/* SEZIONE CRITICA: legge cio' che e' stato messo da Secondo e lo cambia */
		valGlobale=valGlobale+3;

		/* per debug e verifica */
		countPrimo++;
		if( countPrimo != (countSecondo+1) ) {
			printf("Primo ha riconosciuto "
				" una violazione di sincronizzazione \n");
			exit(1);
		}

		printf("Primo scrive %lu \n", valGlobale ); 
		fflush(stdout);

		
		/* stabilisco che il prossimo turno e' di Secondo e risveglio qualcuno.
		   Nel caso che Secondo non abbia ancora fatto la wait
		   la variabile turno==2 dice a Secondo che non deve aspettare
		   perche' Primo ha gia' fatto la signal
		*/
		turno=2;
		DBGpthread_cond_signal(&cond,"1"); 

		/* rilascio mutua esclusione, cosi' Secondo parte */
		DBGpthread_mutex_unlock(&mutex,"1"); 
	}
	printf("Primo termina\n"); 
	pthread_exit(NULL); 
} 

void *Secondo (void *arg) 
{ 
	while(1) {
		/* aspettiamo che il Primo ci risvegli */
		DBGpthread_mutex_lock(&mutex,"2"); 

		while( turno != 2  ) {
			DBGpthread_cond_wait(&cond,&mutex,"2");
			if( turno != 2 )
				DBGpthread_cond_signal(&cond,"2");
		}

		/* SEZIONE CRITICA: legge cio' che e' stato messo da Primo e lo cambia */
		valGlobale=valGlobale+10;

		/* per debug e verifica */
		countSecondo++;
		if( countPrimo != countSecondo ) {
			printf("Secondo ha riconosciuto "
				" una violazione di sincronizzazione con Primo\n");
			exit(1);
		}
		if( countSecondo != (countTerzo+1) ) {
			printf("Secondo ha riconosciuto "
				" una violazione di sincronizzazione con Terzo \n");
			exit(1);
		}


		printf("Secondo scrive %lu \n", valGlobale ); 
		fflush(stdout);

		/* stabilisco che il prossimo turno e' di Terzo e risveglio qualcuno.
		   Nel caso che Terzo non abbia ancora fatto la wait
		   la variabile turno==3 dice a Terzo che non deve aspettare
		   perche' Secondo ha gia' fatto la signal
		*/
		turno=3;
		DBGpthread_cond_signal(&cond,"2"); 

		/* rilascio mutua esclusione, cosi' Terzo parte */
		DBGpthread_mutex_unlock(&mutex,"2"); 
	}
	printf("Secondo termina\n"); 
	pthread_exit(NULL); 
} 

void *Terzo () 
{ 
	while(1) {
		/* aspettiamo che il Secondo ci risvegli */
		DBGpthread_mutex_lock(&mutex,"2"); 

		while( turno != 3  ) {
			DBGpthread_cond_wait(&cond,&mutex,"3");
			if( turno != 3 )
				DBGpthread_cond_signal(&cond,"3");
		}

		/* SEZIONE CRITICA: legge cio' che e' stato messo da Secondo e lo cambia */
		valGlobale=valGlobale+15;

		/* per debug e verifica */
		countTerzo++;
		if( countTerzo != countPrimo ) {
			printf("Terzo ha riconosciuto "
				" una violazione di sincronizzazione con Primo\n");
			exit(1);
		}
		if( countSecondo != countTerzo ) {
			printf("Terzo ha riconosciuto "
				" una violazione di sincronizzazione con Secondo\n");
			exit(1);
		}


		printf("Terzo scrive %lu \n", valGlobale ); 
		fflush(stdout);

		/* stabilisco che il prossimo turno e' di Primo e risveglio qualcuno.
		   Nel caso che Primo non abbia ancora fatto la wait
		   la variabile turno==1 dice a Primo che non deve aspettare
		   perche' Terzo ha gia' fatto la signal
		*/
		turno=1;
		DBGpthread_cond_signal(&cond,"3"); 

		/* rilascio mutua esclusione, cosi' Primo parte */
		DBGpthread_mutex_unlock(&mutex,"3"); 
	}
	printf("Terzo termina\n"); 
	pthread_exit(NULL); 
}

 
int main () 
{ 
	pthread_t    th[NUMTHREADS]; 
	int  rc, i;
	void *ptr; 

	rc = pthread_cond_init(&cond, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* all'inizio Primo non deve aspettare Secondo */
	turno=1;

	rc = pthread_create(&(th[0]), NULL, Primo, NULL); 
	if (rc) PrintERROR_andExit(rc,"pthread_create failed");
	/* do' tempo a Primo di mettersi in attesa 
	   che Secondo lo svegli
	*/
	rc = pthread_create(&(th[1]), NULL, Secondo, NULL); 
	if (rc) PrintERROR_andExit(rc,"pthread_create failed");
	rc = pthread_create(&(th[2]), NULL, Terzo, NULL); 
	if (rc) PrintERROR_andExit(rc,"pthread_create failed");

	for(i=0;i<NUMTHREADS;i++) {
		rc = pthread_join(th[i], &ptr ); 
		if (rc) PrintERROR_andExit(rc,"pthread_join failed");
	}

	rc = pthread_mutex_destroy(&mutex); 
	if(rc) PrintERROR_andExit(rc,"pthread_mutex_destroy failed");
	rc = pthread_cond_destroy(&cond); 
	if(rc) PrintERROR_andExit(rc,"pthread_cond_destroy failed");

	return(0); 
} 
  
  
