/* file:  PiuTipiProdPiuTipiCons1Buffer.c 
   NProduttoriA e MProduttoriB con K ConsumatoriA e L ConsumatoriB
   che si scambiano prodotti mediante un unico Buffer.
   Ipotizzo che la durata di deposito e prelievo sia infinitesima,
   quindi posso far eseguire deposito e prelievo in sezione critica.
   E' la versione efficente perche' 
   - utilizza due pthread_cond_var (una per Prod e una per Cons)
   - sveglia i pthread solo quando hanno qualcosa da fare.
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


#define NUMPRODA 3
#define NUMPRODB  5
#define NUMCONSA  4
#define NUMCONSB  4
#define NUMBUFFER 1	/* NON MODIFICARE MAI */

/* dati da proteggere */
uint64_t valGlobale=0;
char tipo=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   condProd, condCons; 
int numBufferPieni=0; /* 0 o 1 */

void *ProduttoreA (void *arg) 
{ 
	char Plabel[128];
	char Plabelsignal[128];
	int indice;
	uint64_t valProdotto=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"PA%d",indice);
	sprintf(Plabelsignal,"PB%d->C",indice);

	while(1) {
		/* QUI IL PRODUTTORE PRODUCE IL DATO, un intero.
		   supponiamo che il produttore impieghi circa
		   un secondo a produrre il dato
		*/
		DBGsleep(1,Plabel);
		printf("ProdA %s PRODUCE dato %lu\n", Plabel, valProdotto+1 ); 
		fflush(stdout);
		
		valProdotto++;
		
		DBGpthread_mutex_lock(&mutex,Plabel); 

		while (  numBufferPieni >=  NUMBUFFER  )  {
			DBGpthread_cond_wait(&condProd,&mutex,Plabel);
		}
		/* riempie il buffer col dato prodotto prima */
		valGlobale=valProdotto;
		tipo='A';

		printf("ProdA %s inserisce %lu \n", Plabel, valGlobale ); 
		fflush(stdout);

		numBufferPieni++;

		DBGpthread_cond_signal(&condCons,Plabelsignal); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Plabel); 
	}
	pthread_exit(NULL); 
} 

void *ProduttoreB (void *arg) 
{ 
	char Plabel[128];
	char Plabelsignal[128];
	int indice;
	uint64_t valProdotto=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"PB%d",indice);
	sprintf(Plabelsignal,"PB%d->C",indice);

	while(1) {
		/* QUI IL PRODUTTORE PRODUCE IL DATO, un intero.
		   supponiamo che il produttore impieghi circa
		   un secondo a produrre il dato
		*/
		DBGsleep(1,Plabel);
		printf("ProdB %s PRODUCE dato %lu\n", Plabel, valProdotto+1 ); 
		fflush(stdout);
		
		valProdotto++;
		
		DBGpthread_mutex_lock(&mutex,Plabel); 

		while (  numBufferPieni >=  NUMBUFFER  )  {
			DBGpthread_cond_wait(&condProd,&mutex,Plabel);
		}
		/* riempie il buffer col dato prodotto prima */
		valGlobale=valProdotto;
		tipo='B';

		printf("ProdB %s inserisce %lu \n", Plabel, valGlobale ); 
		fflush(stdout);

		numBufferPieni++;
		DBGpthread_cond_signal(&condCons,Plabelsignal); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Plabel); 
	}
	pthread_exit(NULL); 
} 


void *ConsumatoreA (void *arg) 
{ 
	uint64_t val;
	char Clabel[128];
	char Clabelsignal[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Clabel,"CA%d",indice);
	sprintf(Clabelsignal,"CA%d->P",indice);

	
	while(1) {
		DBGpthread_mutex_lock(&mutex,Clabel); 
		while ( numBufferPieni  <= 0 )  {
			DBGpthread_cond_wait(&condCons,&mutex,Clabel);
			if ( numBufferPieni  > 0 )  {
				if( tipo!='A' ) {
					DBGpthread_cond_signal(&condCons,Clabel);
				}
			}
		}
		/* prendo cio' che c'e' nel buffer */
		val=valGlobale;

		printf("ConsA %s legge %lu \n", Clabel, val ); 
		fflush(stdout);

		numBufferPieni--;
		/* risveglio 1 Prod per riempire il buffer svuotato */
		DBGpthread_cond_signal(&condProd,Clabelsignal); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Clabel); 

		/* fuori da mutua esclusione, uso il dato 
		   supponiamo che ci voglia 1 secondo
		*/
		printf("ConsA %s USA dati %lu \n", Clabel, val ); 
		fflush(stdout);
		DBGsleep(1,Clabel);
		
	}
	pthread_exit(NULL); 
} 


void *ConsumatoreB (void *arg) 
{ 
	uint64_t val;
	char Clabel[128];
	char Clabelsignal[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Clabel,"CB%d",indice);
	sprintf(Clabelsignal,"CB%d->P",indice);

	
	while(1) {
		DBGpthread_mutex_lock(&mutex,Clabel); 

		while ( numBufferPieni  <= 0 )  {
			DBGpthread_cond_wait(&condCons,&mutex,Clabel);
			if ( numBufferPieni  > 0 )  {
				if( tipo!='B' ) {
					DBGpthread_cond_signal(&condCons,Clabel);
				}
			}
		}
		/* prendo cio' che c'e' nel buffer */
		val=valGlobale;

		printf("ConsB %s legge %lu \n", Clabel, val ); 
		fflush(stdout);

		numBufferPieni--;

		/* risveglio 1 Prod per riempire il buffer svuotato */
		DBGpthread_cond_signal(&condProd,Clabelsignal); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Clabel); 

		/* fuori da mutua esclusione, uso il dato 
		   supponiamo che ci voglia 1 secondo
		*/
		printf("ConsB %s USA dati %lu \n", Clabel, val ); 
		fflush(stdout);
		DBGsleep(1,Clabel);
		
	}
	pthread_exit(NULL); 
} 

int main ( int argc, char* argv[] ) 
{ 
	pthread_t    th;
	int  rc, i, *intptr;

	rc = pthread_cond_init(&condProd, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condCons, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* all'inizio i Cons devono aspettare il primo Prod */
	numBufferPieni=0; /* 0 o 1 */

	for(i=0;i<NUMCONSA;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,ConsumatoreA,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	for(i=0;i<NUMCONSB;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,ConsumatoreB,(void*)intptr); 
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
  
  
  
