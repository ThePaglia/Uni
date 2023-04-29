/* file:  pista.c   */

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif

#include "printerror.h"

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>
#include <inttypes.h>
#include <pthread.h> 

#include "DBGpthread.h"

#define NUMAEREI 4
/* tempistiche */
#define MINSECDECOLLO 2
#define MAXSECDECOLLO 5
#define MINSECVOLO 6 
#define MAXSECVOLO 10
#define MINSECATTERRAGGIO 4
#define MAXSECATTERRAGGIO 7
#define MINSECRIFORNIMENTO 1
#define MAXSECRIFORNIMENTO 1


/* per fare un po' piu' di output, decommentare questa define */
/* #define DOOUTPUT */

/* dati da proteggere */
/* PRIMA PARTE DA COMPLETARE DA QUI */
/* vostre variabili globali */
/* SUGGERIMENTO: come sapere quanti aerei vogliono atterrare? */
int NumAereiCheVoglionoAtterrare=0;


/* FINE PRIMA PARTE DA COMPLETARE FINO A QUI */
int PistaLibera=1;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex;
pthread_cond_t	cond;

void attendi( int secmin, int secmax) {
        int secrandom=0;
	static int inizializzato=0;
	if( inizializzato==0 ) {
		srandom( time(NULL) );
		inizializzato=1;
	}
        if( secmin > secmax ) return;
        else if ( secmin == secmax )
                secrandom = secmin;
        else
                secrandom = secmin + ( random()%(secmax-secmin+1) );
        do {
                /* printf("attendi %i\n", secrandom);fflush(stdout); */
                secrandom=sleep(secrandom);
                if( secrandom>0 )
                        { printf("sleep interrupted - continue\n"); fflush(stdout); }
        } while( secrandom>0 );
        return;
}

void *Aereo (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"A%" PRIiPTR "", indice);

	while(1) {
		/* rifornimento */
#ifdef DOOUTPUT
		printf("aereo %s comincia rifornimento\n", Plabel);
		fflush(stdout);
#endif
		attendi( MINSECRIFORNIMENTO , MAXSECRIFORNIMENTO );

#ifdef DOOUTPUT
		printf("aereo %s finisce riforniment\n", Plabel);
		fflush(stdout);
#endif

		printf("aereo %s vorrebbe decollare\n", Plabel);
		fflush(stdout);
		DBGpthread_mutex_lock(&mutex,Plabel); 

		/* INIZIO SECONDA PARTE DA COMPLETARE A PARTIRE DA QUI */
		/* attesa di poter decollare
		 * dare precedenza a chi deve atterrare
		 */

		while( PistaLibera!=1 || NumAereiCheVoglionoAtterrare > 0 ) {
			printf("aereo %s attende di decollare\n", Plabel);
			fflush(stdout);
			DBGpthread_cond_wait(&cond,&mutex,Plabel);
		}

		/* FINE SECONDA PARTE DA COMPLETARE FINO A QUI */
		/* ora puo' iniziare il decollo */

		/* aereo impegna pista per decollo */
		PistaLibera=0;
		printf("aereo %s inizia decollo\n", Plabel);
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 
		/* operazione di decollo */
		attendi( MINSECDECOLLO , MAXSECDECOLLO );
		DBGpthread_mutex_lock(&mutex,Plabel); 
		/* fine decollo, libero pista e avviso che pista e' libera */
		PistaLibera=1;
		/* aereo avvisa che pista e' libera */
		DBGpthread_cond_broadcast(&cond,Plabel);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		/* VOLO */
		printf("aereo %s vola\n", Plabel);
		fflush(stdout);
		attendi( MINSECVOLO , MAXSECVOLO );
		printf("aereo %s fine volo\n", Plabel);
		fflush(stdout);

		/* aereo vorrebbe atterrare */
		printf("aereo %s VORREBBE ATTERRARE\n", Plabel);
		fflush(stdout);

		DBGpthread_mutex_lock(&mutex,Plabel); 
		/* INIZIO TERZA PARTE DA COMPLETARE A PARTIRE DA QUI */

		/* aereo dichiara di voler atterrare */
		NumAereiCheVoglionoAtterrare++;

		/* FINE TERZA PARTE DA COMPLETARE FINO A QUI */

		/* aereo attende pista libera */
		while( PistaLibera!=1 ) {
			printf("aereo %s attende di atterrare\n", Plabel);
			fflush(stdout);
			DBGpthread_cond_wait(&cond,&mutex,Plabel);
		}
		/* aereo occupa pista e inizia atterraggio */
		PistaLibera=0;


		/* ATTERRAGGIO */
		printf("aereo %s INIZIA ATTERRAGGIO\n", Plabel);
		fflush(stdout);

		DBGpthread_mutex_unlock(&mutex,Plabel); 
		attendi( MINSECATTERRAGGIO , MAXSECATTERRAGGIO );
		printf("aereo %s FINISCE ATTERRAGGIO\n", Plabel);
		fflush(stdout);

		DBGpthread_mutex_lock(&mutex,Plabel); 

		/* QUARTA PARTE DA COMPLETARE DA QUI */

		/* aereo informa di essere atterrato
		 * a favore di chi vorrebbe decollare */
		NumAereiCheVoglionoAtterrare--;

		/* FINE QUARTA PARTE DA COMPLETARE FINO A QUI */

		/* aereo libera pista */
		PistaLibera=1;
		/* aereo avvisa che pista e' libera */
		DBGpthread_cond_broadcast(&cond,Plabel);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		/* aereo va a rifornirsi */

	} /* fine while */
}

int main ( int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc;
	intptr_t i;

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_cond_init(&cond, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	/* creo i thread aerei */
	for(i=0; i<NUMAEREI; i++) {
		rc=pthread_create(&th, NULL, Aereo, (void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL);
	return(0); 
} 
  
  
  
