/* file:  2categorieCons.c 
   NProduttori e 2 categorie di Consumatori
   che si scambiano prodotti mediante un unico Buffer.
   Origina dalla versione NON efficente
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

/* commentato per fare meno output
#define FAIOUTPUT
*/

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>
#include <pthread.h> 
#include "printerror.h"
#include "DBGpthread.h"

#define NUMPROD 10
#define NUMCONSA 3
#define NUMCONSB 4
#define NUMBUFFER 1	/* NON MODIFICARE MAI */

typedef struct parametri_categoria {
	int indice;
	char categoria;
} Parameter;

/* dati da proteggere */
uint64_t valGlobale=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   condProd, condConsA, condConsB; 
int numBufferPieni=0; /* 0 o 1 */
char turnoCons='A';

void *Produttore (void *arg) 
{ 
	char Plabel[128];
	int indice;
	uint64_t valProdotto=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"P%d",indice);

	while(1) {
		/* QUI IL PRODUTTORE PRODUCE IL DATO, un intero.
		   supponiamo che il produttore impieghi circa un secondo
		   a produrre il dato fuori da mutua esclusione
		*/
		DBGsleep(1,Plabel);
#ifdef FAIOUTPUT
		printf("Prod %s PRODUCE dato %lu\n", Plabel, valProdotto+1 ); 
		fflush(stdout);
#endif
		
		valProdotto++;
		
		DBGpthread_mutex_lock(&mutex,Plabel); 

		while ( numBufferPieni >= NUMBUFFER )
			DBGpthread_cond_wait(&condProd,&mutex,Plabel);

		/* riempie il buffer col dato prodotto prima,
		   in realta' per semplicita' incremento la var
		   Impiego un secondo a depositare il dato in mutua esclusione.
		*/
		valGlobale=valProdotto;

		printf("Prod %s inserisce %lu \n", Plabel, valGlobale ); 
		fflush(stdout);
		DBGsleep(1,Plabel);

		numBufferPieni++;

		/* risveglio un Consumatore della categoria di turno */
		if( turnoCons=='A' )
			DBGpthread_cond_signal(&condConsA,Plabel); 
		else
			DBGpthread_cond_signal(&condConsB,Plabel); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Plabel); 
	}
	pthread_exit(NULL); 
} 

void *Consumatore (void *arg) 
{ 
	uint64_t val;
	char Clabel[128];
	int indice;
	int miacategoria;

	indice=((Parameter*)arg)->indice;
	miacategoria=((Parameter*)arg)->categoria;
	free(arg);
	sprintf(Clabel,"C%i%c",indice, miacategoria);
	
	while(1) {
		DBGpthread_mutex_lock(&mutex,Clabel); 

		while( numBufferPieni <= 0 || turnoCons!=miacategoria ) { 
			if(miacategoria=='A')
				DBGpthread_cond_wait(&condConsA,&mutex,Clabel);
			else
				DBGpthread_cond_wait(&condConsB,&mutex,Clabel);
		}

		/* prendo cio' che c'e' nel buffer, impiegando un secondo */
		val=valGlobale;

		printf("Cons %s legge %lu \n", Clabel, val ); 
		fflush(stdout);
		DBGsleep(1,Clabel);

		numBufferPieni--;
		/* passo il turno all'altra categoria */
		if( turnoCons=='A' )	turnoCons='B';
		else			turnoCons='A';

		DBGpthread_cond_signal(&condProd,Clabel); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Clabel); 

		/* fuori da mutua esclusione, uso il dato 
		   supponiamo che ci voglia 1 secondo
		*/
#ifdef FAIOUTPUT
		printf("Cons %s USA dato %lu \n", Clabel, val ); 
		fflush(stdout);
#endif
		DBGsleep(1,Clabel);
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i;

	DBGpthread_cond_init(&condProd, NULL, "main");
	DBGpthread_cond_init(&condConsA, NULL, "main");
	DBGpthread_cond_init(&condConsB, NULL, "main");
	DBGpthread_mutex_init(&mutex, NULL, "main"); 

	/* all'inizio i Cons devono aspettare il primo Prod */
	numBufferPieni=0; /* 0 o 1 */
	/* i primi a consumare sono i consumatori A */
	turnoCons='A';

	for(i=0;i<NUMCONSA;i++) {
		Parameter *ptr;
		ptr=(Parameter*)malloc(sizeof(Parameter));
		if( !ptr ) { printf("malloc failed\n");exit(1); }
		ptr->indice=i;
		ptr->categoria='A';
		rc=pthread_create( &th,NULL,Consumatore,(void*)ptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	for(i=0;i<NUMCONSB;i++) {
		Parameter *ptr;
		ptr=(Parameter*)malloc(sizeof(Parameter));
		if( !ptr ) { printf("malloc failed\n");exit(1); }
		ptr->indice=i;
		ptr->categoria='B';
		rc=pthread_create( &th,NULL,Consumatore,(void*)ptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMPROD;i++) {
		int *intptr;
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Produttore,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
