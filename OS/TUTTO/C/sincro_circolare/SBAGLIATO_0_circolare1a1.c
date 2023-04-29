/* file:  SBAGLIATO_0_circolare1a1.c 
   Routine che fornisce due passaggi di testimone tra due processi
   il primo fa partire il secondo e aspetta 
   che il secondo faccia partire lui (il primo)
   e cosi' via all'infinito.
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

#define NUMTHREADS 2

/* dati da proteggere */
uint64_t valGlobale=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   cond; 
int PrimoFaiWait, SecondoFaiWait;

/* variabili di debug, per verificare che
   la mutua esclusione e la sincronizzazione
   funzionino bene.
   Contano quante volte i due pthread
   accedono alla variabile globale da proteggere.
*/
uint64_t countPrimo=0, countSecondo=0;

void *Primo (void *arg) 
{ 
	while(1) {
		/* aspettiamo che il secondo ci risvegli */
		DBGpthread_mutex_lock(&mutex,"1"); 

		/* legge cio' che e' stato messo da Secondo e lo cambia */
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


		/* rilascio mutua esclusione, cosi' Secondo parte */
		DBGpthread_mutex_unlock(&mutex,"1"); 
	}
	printf("Primo termina\n"); 
	pthread_exit(NULL); 
} 

void *Secondo (void *arg) 
{ 
	/* all'inizio Primo non si mette in attesa
	   perche' e' stato settato PrimoFaiWait=0
	   */
	while(1) {
		/* aspettiamo che il Primo ci risvegli */
		DBGpthread_mutex_lock(&mutex,"2"); 

		/* legge cio' che e' stato messo da Primo e lo cambia */
		valGlobale=valGlobale+10;

		/* per debug e verifica */
		countSecondo++;
		if( countPrimo != countSecondo ) {
			printf("Secondo ha riconosciuto "
				" una violazione di sincronizzazione \n");
			exit(1);
		}

		printf("Secondo scrive %lu \n", valGlobale ); 
		fflush(stdout);


		/* rilascio mutua esclusione, cosi' Primo parte */
		DBGpthread_mutex_unlock(&mutex,"2"); 
	}
	printf("Secondo termina\n"); 
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
	PrimoFaiWait=0;
	SecondoFaiWait=1;
	rc = pthread_create(&(th[0]), NULL, Primo, NULL); 
	if (rc) PrintERROR_andExit(rc,"pthread_create failed");
	/* do' tempo a Primo di mettersi in attesa 
	   che Secondo lo svegli
	*/
	rc = pthread_create(&(th[1]), NULL, Secondo, NULL); 
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
  
  
  
