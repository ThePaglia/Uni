/* file:  soccorsi_semplice.c */

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#endif

#include "printerror.h"

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>
#include <inttypes.h>
#include <pthread.h> 

#include "DBGpthread.h"

#define NUMPROF 1
#define NUMMEDICI 1
#define NUMESORCISTI 1

/* dati da proteggere */
pthread_mutex_t  mutex;
int		 numRichiesteAiutoNonSoddisfatte=0;
int medicoArrivato=0;
int esorcistaArrivato=0;
pthread_cond_t   condRichiestaAiuto;
pthread_cond_t condInizioSoccorso;
pthread_cond_t condFineSoccorso;

void *Esorcista (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"Eso%" PRIiPTR "",indice);

	while(1) {
		DBGpthread_mutex_lock(&mutex,Plabel); 
		while(	numRichiesteAiutoNonSoddisfatte==0 ) {
			DBGpthread_cond_wait(&condRichiestaAiuto,&mutex,Plabel);
		}
		printf("Esorcista %s arriva da prof \n", Plabel );
		fflush(stdout);
		esorcistaArrivato=1;
		if( medicoArrivato == 1 ) {
			/* c'e' gia' il medico, fermo su condInizioSoccorso, lo sveglio */
			DBGpthread_cond_signal(&condInizioSoccorso,Plabel); 
			/* ora il prof ha entrambi i soccorritori */
			numRichiesteAiutoNonSoddisfatte--;
		}
		else { /* non c'e' il medico, aspetto prima di soccorrere */
			DBGpthread_cond_wait(&condInizioSoccorso,&mutex,Plabel);
		}
		printf("esorcista %s inizia soccorso a prof \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 
		/* tempo per soccorrere */
		sleep(2);
	
		DBGpthread_mutex_lock(&mutex,Plabel); 
		esorcistaArrivato=0; /* esorcista ha finito */
		if( medicoArrivato == 0 ) {
			/* anche medico ha finito, avviso professore che puo' andare */
			DBGpthread_cond_signal(&condFineSoccorso,Plabel); 
		}
		printf("esorcista %s finisce soccorso a prof \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

	}
	pthread_exit(NULL); 
} 


void *Medico (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"Med%" PRIiPTR "",indice);

	while(1) {
		DBGpthread_mutex_lock(&mutex,Plabel); 
		while(	numRichiesteAiutoNonSoddisfatte==0 ) {
			DBGpthread_cond_wait(&condRichiestaAiuto,&mutex,Plabel);
		}
		printf("Medico %s arriva da prof \n", Plabel );
		fflush(stdout);
		medicoArrivato=1;
		if( esorcistaArrivato == 1 ) {
			/* c'e' gia' l'esorcista, fermo su condInizioSoccorso, lo sveglio */
			DBGpthread_cond_signal(&condInizioSoccorso,Plabel); 
			/* ora il prof ha entrambi i soccorritori */
			numRichiesteAiutoNonSoddisfatte--;
		}
		else { /* non c'e' l'esorcista, aspetto prima di soccorrere */
			DBGpthread_cond_wait(&condInizioSoccorso,&mutex,Plabel);
		}
		printf("medico %s inizia soccorso a prof \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 
		/* tempo per soccorrere */
		sleep(2);
	
		DBGpthread_mutex_lock(&mutex,Plabel); 
		medicoArrivato=0; /* medico ha finito */
		if( esorcistaArrivato == 0 ) {
			/* anche esorcista ha finito, avviso professore che puo' andare */
			DBGpthread_cond_signal(&condFineSoccorso,Plabel); 
		}
		printf("medico %s finisce soccorso a prof \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

	}
	pthread_exit(NULL); 
} 

void *Professore (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"Prof%" PRIiPTR "",indice);

	while(1) {
		DBGpthread_mutex_lock(&mutex,Plabel); 
		printf("prof %s cade e chiede aiuto \"Porc... @#! Vac...\n", Plabel );
		fflush(stdout);
		/* prof cade e chiede aiuto */
		numRichiesteAiutoNonSoddisfatte++;
		DBGpthread_cond_broadcast(&condRichiestaAiuto,Plabel); 

		/* prof aspetta fine del soccorso */
		DBGpthread_cond_wait(&condFineSoccorso,&mutex,Plabel);
		printf("prof %s puo' rialzarsi:   \"GRAZIE\" \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		/* prof ci mette un po' a rialzarsi */
		sleep(1);

		/* prof, se ne va a fare lezione */
		sleep(4);
	}
	pthread_exit(NULL); 
} 

int main ( int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc;
	intptr_t i;

	numRichiesteAiutoNonSoddisfatte=0;
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_cond_init(&condRichiestaAiuto, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_cond_init(&condInizioSoccorso, NULL);
	if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	rc = pthread_cond_init(&condFineSoccorso, NULL);
	if(rc) PrintERROR_andExit(rc,"pthread_create failed");

	for(i=0;i<NUMPROF;i++) {
		rc=pthread_create(&th,NULL,Professore,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMMEDICI;i++) {
		rc=pthread_create(&th,NULL,Medico,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMESORCISTI;i++) {
		rc=pthread_create(&th,NULL,Esorcista,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL);
	return(0); 
} 
  
