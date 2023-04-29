/* file:  es8_consA+B.c  sez. critica di durata NON infinitesima,
	prodA e prodB DEVONO depositare POSSIBILMENTE IN PARALLELO
	per risparmiare tempo.
*/ 

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#else
#if _POSIX_C_SOURCE < 200112L
	#undef  _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#endif
#endif


#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>	/* uint64_t intptr_t */
#include <inttypes.h>	/* uint64_t  PRIiPTR */
#include <sys/time.h>	/* gettimeofday()    struct timeval */
#include <pthread.h> 

#include "printerror.h"
#include "DBGpthread.h"

#define NUMPRODA 3
#define NUMPRODB 5
#define NUMCONS 10 
#define NUMBUFFER 1	/* NON MODIFICARE MAI */

/* dati da proteggere */
uint64_t valGlobaleA=0;
uint64_t valGlobaleB=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   condProdA, condProdB, condCons; 
int numBufferAPieni=0; /* 0 o 1 */
int numBufferBPieni=0; /* 0 o 1 */
int accessoBufferA=0, accessoBufferB=0;

void *ProduttoreA (void *arg) 
{ 
	char Plabel[128];
	int indice;
	uint64_t valProdottoA=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"PA%d",indice);

	while(1) {

		/* QUI IL PRODUTTORE A PRODUCE IL DATO, un intero.
		   supponiamo che il produttore impieghi circa
		   un secondo a produrre il dato
		printf("ProdA %s PRODUCE dato %lu\n",Plabel,valProdottoA+1 ); 
		fflush(stdout);
		*/
		DBGsleep(1,Plabel);

		valProdottoA++;

		DBGpthread_mutex_lock(&mutex,Plabel); 
	
		while (  numBufferAPieni >=  NUMBUFFER || accessoBufferA==1 ) 
		         DBGpthread_cond_wait (  &condProdA, &mutex, Plabel );
		accessoBufferA=1;
		DBGpthread_mutex_unlock (  &mutex, Plabel ); /* rilascio mutua esclusione */

		/* SEZ CRITICA : riempie il buffer A col dato prodotto */
		printf("ProdA %s inizia inserimento dato %lu\n",Plabel,valProdottoA ); 
		fflush(stdout);
		DBGsleep(1,Plabel);
		valGlobaleA = valProdottoA;
		printf("ProdA %s finisce inserimento dato %lu\n",Plabel,valProdottoA ); 
		fflush(stdout);
		/* fine SEZ CRITICA : */

		DBGpthread_mutex_lock(&mutex,Plabel); 
		numBufferAPieni++;
		accessoBufferA=0;

		DBGpthread_cond_signal (  &condCons, Plabel );  /* risveglio 1 Cons per svuotare 1 buffer */
		DBGpthread_mutex_unlock (  &mutex, Plabel ); /* rilascio mutua esclusione */
	}
	
	pthread_exit(NULL); 
} 

void *ProduttoreB (void *arg) 
{ 
	char Plabel[128];
	int indice;
	uint64_t valProdottoB=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"PB%d",indice);

	while(1) {
		/* QUI IL PRODUTTORE B PRODUCE IL DATO, un intero.
		   supponiamo che il produttore impieghi circa
		   un secondo a produrre il dato
		printf("ProdB %s PRODUCE dato %lu\n",Plabel,valProdottoB+1 ); 
		fflush(stdout);
		*/

		DBGsleep(1,Plabel);
		valProdottoB++;
	
		DBGpthread_mutex_lock(&mutex,Plabel); 

		while (  numBufferBPieni >=  NUMBUFFER || accessoBufferB==1 ) 
		         DBGpthread_cond_wait (  &condProdB, &mutex, Plabel );
		accessoBufferB=1;
		DBGpthread_mutex_unlock (  &mutex, Plabel ); /* rilascio mutua esclusione */

		/* SEZ CRITICA : riempie il buffer B col dato prodotto */
		printf("ProdB %s inizia inserimento dato %lu\n",Plabel,valProdottoB ); 
		fflush(stdout);
		DBGsleep(1,Plabel);
		valGlobaleB = valProdottoB;   /* SEZ CRITICA : riempie il buffer B col dato prodotto */
		printf("ProdB %s finisce inserimento dato %lu\n",Plabel,valProdottoB ); 
		fflush(stdout);
		/* fine SEZ CRITICA : */

		DBGpthread_mutex_lock(&mutex,Plabel); 
		numBufferBPieni++;
		accessoBufferB=0;

		DBGpthread_cond_signal (  &condCons, Plabel );  /* risveglio 1 Cons per svuotare 1 buffer */
		DBGpthread_mutex_unlock (  &mutex, Plabel ); /* rilascio mutua esclusione */
	}
	
	pthread_exit(NULL); 
} 


void *Consumatore (void *arg) 
{ 
	uint64_t val=0;
	char Clabel[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Clabel,"C%d",indice);

	
	while(1) {
		DBGpthread_mutex_lock(&mutex,Clabel); 

		while (  ( numBufferAPieni  <= 0 )  ||  ( numBufferBPieni <= 0 )  ||
			 ( accessoBufferA  != 0 )   ||  ( accessoBufferB != 0 )        )
		         DBGpthread_cond_wait (  &condCons, &mutex, Clabel );

		accessoBufferA=1;
		accessoBufferB=1;
		DBGpthread_mutex_unlock (  &mutex, Clabel ); /* rilascio mutua esclusione */

		/* SEZ CRITICA : prende cio' che sta nei due buffer A e B */
		printf("Cons %s inizia prelievo dato A %lu e dato B %lu \n",Clabel,valGlobaleA, valGlobaleB ); 
		fflush(stdout);
		DBGsleep(1,Clabel);
		val = valGlobaleA+valGlobaleB;
		printf("Cons %s finisce prelievo dato A %lu e dato B %lu \n",Clabel,valGlobaleA, valGlobaleB ); 
		fflush(stdout);

		DBGpthread_mutex_lock(&mutex,Clabel); 
		numBufferAPieni--;
		numBufferBPieni--;
		accessoBufferA=0;
		accessoBufferB=0;
		DBGpthread_cond_signal (  &condProdA, Clabel );    /* risveglio 1 ProdA per riempire il buffer */
		DBGpthread_cond_signal (  &condProdB, Clabel );    /* risveglio 1 ProdB per riempire il buffer */
		DBGpthread_mutex_unlock (  &mutex, Clabel ); /* rilascio mutua esclusione */

		DBGsleep(1,Clabel);
		/* */
		printf("Cons %s consuma dato %lu\n",Clabel,val ); 
		fflush(stdout)
		/* */;
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;

	rc = pthread_cond_init(&condProdA, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condProdB, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condCons, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* all'inizio i Cons devono aspettare il primo Prod */
	numBufferAPieni=0; /* 0 o 1 */
	numBufferBPieni=0; /* 0 o 1 */

	for(i=0;i<NUMCONS;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Consumatore,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMPRODA;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,ProduttoreA,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMPRODB;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,ProduttoreB,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
