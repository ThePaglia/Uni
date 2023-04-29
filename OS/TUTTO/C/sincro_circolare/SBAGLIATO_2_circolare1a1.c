/* file:  SBAGLIATO_2_circolare1a1.c 
   Processo che implementa due passaggi di testimone
   tra due pthread ripetutamente
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

pthread_mutex_t  mutex; 
pthread_cond_t   cond; 

uint64_t valGlobale=0;

void *Primo () 
{ 
	while(1) {
		/* aspettiamo che il secondo ci risvegli */
		DBGpthread_mutex_lock(&mutex,"1"); 
		DBGpthread_cond_wait(&cond,&mutex,"1");

		/* legge cio' che e' stato messo da Secondo e lo cambia */
		valGlobale=valGlobale+3;

		printf("Primo scrive %lu \n", valGlobale ); 
		fflush(stdout);

		/* risveglio Secondo */
		DBGpthread_cond_signal(&cond,"1"); 
		/* rilascio mutua esclusione, cosi' Secondo parte */
		DBGpthread_mutex_unlock(&mutex,"1"); 
		
		DBGsleep(1,"1");
	}
	pthread_exit(NULL); 
} 

void *Secondo () 
{ 
	/* all'inizio il Primo si mette in attesa
	   ed il Secondo deve iniziare risvegliandolo
	   */
	DBGpthread_mutex_lock(&mutex,"2"); 
	/* risveglio Primo */
	DBGpthread_cond_signal(&cond,"2"); 
	/* rilascio mutua esclusione, cosi' Primo parte */
	DBGpthread_mutex_unlock(&mutex,"2"); 
	
	while(1) {
		/* aspettiamo che il Primo ci risvegli */
		DBGpthread_mutex_lock(&mutex,"2"); 
		DBGpthread_cond_wait(&cond,&mutex,"2");

		/* legge cio' che e' stato messo da Primo e lo cambia */
		valGlobale=valGlobale+10;

		printf("Secondo scrive %lu \n", valGlobale ); 
		fflush(stdout);

		/* risveglio Primo */
		DBGpthread_cond_signal(&cond,"2"); 
		/* rilascio mutua esclusione, cosi' Primo parte */
		DBGpthread_mutex_unlock(&mutex,"2"); 
		
		DBGsleep(1,"2");
	}
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

	rc = pthread_create(&(th[0]), NULL, Primo, NULL); 
	if (rc) PrintERROR_andExit(rc,"pthread_create failed");

	/* do' tempo a Primo di mettersi in attesa la prima volta
	   prima che il Secondo lo svegli
	*/
	DBGsleep(1,"main");

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
  
  
  
