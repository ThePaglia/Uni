/* file:   attacchinisincroni.c
*/ 

/* 
	la   #define _POSIX_C_SOURCE 200112L   e' dentro printerror.h 
	anche la   #define _THREAD_SAFE    e' dentro printerror.h 

*/

#include "printerror.h"

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>	/* uint64_t */
#include <sys/time.h>	/* gettimeofday()    struct timeval */
#include <pthread.h> 

#include "DBGpthread.h"

#define NUMVECCHIETTI 4
#define NUMATTACCHINI 2

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex;    /* protegge l'accesso alle variabili contatore*/ 
pthread_cond_t condAttacchini;	/* coda in attesa per lavorare */
pthread_cond_t condAttesaArrivoAttacchino2;	/* attesa per arrivo del secondo attacchino */
pthread_cond_t condAttesaFineAttacchino2;	/* attesa per fine del lavoro del secondo attacchino */
pthread_cond_t condVecchietti;	/* coda in attesa per leggere */
int numAttacchiniInAttesa=0;	/* num attacchini in attesa di lavorare */
int numVecchiettiInAttesa=0;	/* num vecchietti in attesa di leggere */
int numAttacchiniAlLavoro=0;	/* num attacchini al lavoro */
int numVecchiettiInLettura=0;  /* numero vecchietti che stanno leggendo */

void *Attacchino (void *arg) 
{ 
	char Alabel[128];
	char Alabelsignal[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Alabel,"A%d",indice);
	sprintf(Alabelsignal,"A%d->",indice);

	while(1) {

		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock( &mutex, Alabel );
		numAttacchiniInAttesa++;

		printf("Attacchino %s vorrebbe lavorare\n", Alabel ); 
		fflush(stdout);

		/* se c'e' qualche vecchietto che sta ancora leggendo
		   allora devo aspettare e mettermi in coda
		   NOTARE CHE 
			NON CONTROLLO SE ci sono vecchietti in attesa, passo avanti io!
			NON CONTROLLO SE c'e' uno attacchino che sta scrivendo perche' siamo solo in due
			e dobbiamo lavorare assieme.
		*/
		while (   numVecchiettiInLettura  > 0   ) {
			printf("Attacchino %s costretto ad attendere\n", Alabel ); 
			fflush(stdout);

			DBGpthread_cond_wait( &condAttacchini, &mutex, Alabel);
		}

		numAttacchiniAlLavoro++;
		numAttacchiniInAttesa--;
		/* se sono l'attacchino arrivato per primo, devo aspettare il secondo attacchino */
		if( numAttacchiniAlLavoro==1)
			DBGpthread_cond_wait( &condAttesaArrivoAttacchino2, &mutex, Alabel);
		else {	/* numAttacchiniAlLavoro==2 
			se sono l'attacchino arrivato per secondo, devo svegliare il primo 
			*/
			DBGpthread_cond_signal( &condAttesaArrivoAttacchino2, Alabel);
		}
		/* rilascio mutua esclusione sulle variabili */
		DBGpthread_mutex_unlock( &mutex, Alabel);

		printf("Attacchino %s comincia ad attaccare manifesti\n", Alabel ); 
		fflush(stdout);
		/* l' attacchino impiega  2 secondi ad attaccare manifesti */
		DBGsleep( 2 , Alabel );

		printf("Attacchino %s finisce di attaccare manifesti \n", Alabel ); 
		fflush(stdout);


		DBGpthread_mutex_lock( &mutex, Alabel);
		numAttacchiniAlLavoro--;
		if ( (numAttacchiniAlLavoro == 0) && (numAttacchiniInAttesa == 0) )  {
			if (  numVecchiettiInAttesa > 0 )  {
				DBGpthread_cond_broadcast ( &condVecchietti, Alabel );
			}
		}
		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock( &mutex, Alabel );

		/* QUI L' ATTACCHINO VA A PRENDERE ALTRI MANIFESTI */
		printf("Attacchino %s prende altri manifesti\n", Alabel ); 
		fflush(stdout);
		DBGsleep( 2 ,Alabel);
	}
	pthread_exit(NULL); 
} 

void *Vecchietto (void *arg) 
{ 
	char Vlabel[128];
	char VlabelbroadcastV[128];
	char VlabelbroadcastA[128];
	int indice;
	int tempogiro;

	indice=*((int*)arg);
	free(arg);
	sprintf(Vlabel,"V%d",indice);
	sprintf(VlabelbroadcastV,"V%d->V",indice);
	sprintf(VlabelbroadcastA,"V%d->A",indice);

	
	while(1) {
		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock(&mutex,Vlabel); 

		/* se ci sono attacchini al lavoro aspetto	
		   se ci sono attacchini in attesa, faccio passare avanti loro 
		*/	
		numVecchiettiInAttesa++;
		while ( numAttacchiniInAttesa > 0 ||  numAttacchiniAlLavoro > 0 )  {
			/* mi metto in coda */
			DBGpthread_cond_wait( &condVecchietti, &mutex, Vlabel );
		}
		/* arrivo qui se non ci sono piu' attacchini in attesa o al lavoro  */
		  
		/* ora vado in lettura anche io */
		numVecchiettiInLettura++;
		numVecchiettiInAttesa--;

		/* ma permetto ad altri vecchietti di vedere come stanno le cose */
		/* rilascio la mutua esclusione sulla variabile dei vecchietti */
		DBGpthread_mutex_unlock ( &mutex, Vlabel );

		/* FUORI DA SEZIONE CRITICA: leggo la bacheca */

		printf("Vecchietto %s legge bacheca \n", Vlabel ); 
		fflush(stdout);

		/* il vecchietto legge per 1 sec */
		DBGsleep( 1 , Vlabel );

		printf("Vecchietto %s finisce di leggere \n", Vlabel ); 
		fflush(stdout);
		/* FINE SEZIONE CRITICA */

		/* riprendo la mutua esclusione sulla variabile dei vecchietti */
		DBGpthread_mutex_lock(&mutex,Vlabel); 
		/* io non leggo piu' */
		numVecchiettiInLettura--;
		if( numVecchiettiInLettura==0 &&  numAttacchiniInAttesa>0 ) 
			DBGpthread_cond_broadcast ( &condAttacchini, VlabelbroadcastA );
		else if ( numVecchiettiInAttesa>0 )
			DBGpthread_cond_broadcast ( &condVecchietti, VlabelbroadcastV );
		
		/* rilascio la mutua esclusione sulle variabili */
		DBGpthread_mutex_unlock(&mutex,Vlabel); 


		/* fuori da mutua esclusione, vecchietto fa un giro, per un numero di secondi pari a 1+(indice%3) */
		tempogiro=1+(indice%3);
		printf("Vecchietto %s va a spasso per %i secondi \n", Vlabel, tempogiro ); 
		fflush(stdout);
		DBGsleep(tempogiro,Vlabel);
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_cond_init(&condAttacchini, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condAttesaArrivoAttacchino2, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condAttesaFineAttacchino2, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condVecchietti, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	numVecchiettiInLettura=0;	/* all'inizio nessun vecchietto sta leggendo */
	numAttacchiniInAttesa=0;	/* all'inizio nessuno attacchino in attesa di lavorare */
	numVecchiettiInAttesa=0;	/* all'inizio nessun vecchietto in attesa di leggere */

	for(i=0;i<NUMVECCHIETTI;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Vecchietto,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMATTACCHINI;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Attacchino,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
