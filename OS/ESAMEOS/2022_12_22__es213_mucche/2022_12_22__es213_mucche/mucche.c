/* file:   mucche.c
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

#define NUMMUCCHE 4

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex;    /* protegge l'accesso alle variabili contatore*/ 
pthread_cond_t condAllevatore;	/* coda in attesa per rifornire */
pthread_cond_t condMucche;	/* coda in attesa per mangiare */
int AllevatoreVuoleRifornireOStaRifornendo=0;	/* allevatore vuole rifornire */
int numMuccheCheMangiano=0;  /* numero mucche che stanno mangiando */

/* 1: SE VOLETE AGGIUNGETE VOSTRE VARIABILI */


/* 1: FINE AGGIUNTA VOSTRE VARIABILI */

void *Allevatore (void *arg) 
{ 
	char Label[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Label,"A%d",indice);

	while(1) {

		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock( &mutex, Label );
		AllevatoreVuoleRifornireOStaRifornendo++;

		printf("Allevatore %s vorrebbe rifornire\n", Label ); 
		fflush(stdout);

		/* se c'e' qualche mucca che sta ancora mangiando
		   allora devo aspettare e mettermi in coda
		   NOTARE CHE NON CONTROLLO SE ci sono mucche in attesa,
		   passo avanti io!
		*/
		while (   numMuccheCheMangiano  > 0   ) {
			printf("Allevatore %s costretto ad attendere\n", Label ); 
			fflush(stdout);

			DBGpthread_cond_wait( &condAllevatore, &mutex, Label);
		}
		/* rilascio mutua esclusione sulle variabili */
		DBGpthread_mutex_unlock( &mutex, Label);

		printf("Allevatore %s comincia a riempire la mangiatoia\n", Label ); 
		fflush(stdout);
		/* l'allevatore impiega  2 secondi a rifornire */
		DBGsleep( 4 , Label );

		printf("Allevatore %s finisce di rifornire \n", Label ); 
		fflush(stdout);

		/* sveglio mucche in attesa di mangiare per farle mangiare */
		DBGpthread_mutex_lock( &mutex, Label);
		AllevatoreVuoleRifornireOStaRifornendo=0;
		DBGpthread_cond_broadcast ( &condMucche, Label );
		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock( &mutex, Label );

		/* QUI L' ALLEVATORE VA A PRENDERE ALTRO FIENO */
		printf("Allevatore %s prende altro mangime\n", Label ); 
		fflush(stdout);
		DBGsleep( 2 ,Label);
	}
	pthread_exit(NULL); 
} 

void *Mucca (void *arg) 
{ 
	char Label[128];
	int indice;
	int tempogiro;

	indice=*((int*)arg);
	free(arg);
	sprintf(Label,"V%d",indice);

	
	while(1) {
		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock(&mutex,Label); 

		/* se l'allevatore vuole rifornire o sta rifornendo aspetto	*/
		while ( AllevatoreVuoleRifornireOStaRifornendo > 0 )  {
			/* mi metto in coda */
			DBGpthread_cond_wait( &condMucche, &mutex, Label );
		}
		/* arrivo qui se non ci sono piu' allevatori che riforniscono
		   o che vogliono rifornire la mangiatoia  */
		  
		/* 2: AGGIUNGERE VOSTRO CODICE PER FAR MANGIARE LE MUCCHE */

		/* ora la mucca comincia a mangiare */
		numMuccheCheMangiano++;

		/* ma permetto ad altre mucche di vedere come stanno le cose */
		/* rilascio la mutua esclusione */
		DBGpthread_mutex_unlock ( &mutex, Label );
		/* 2: FINE AGGIUNTA VOSTRO CODICE PER FAR MANGIARE LE MUCCHE */


		/* FUORI DA SEZIONE CRITICA: mangio */
		printf("Mucca %s mangia \n", Label ); 
		fflush(stdout);

		/* la mucca mangia per circa 4 secondi  sec */
		DBGsleep( 4 , Label );

		printf("Mucca %s finisce di mangiare \n", Label ); 
		fflush(stdout);
		/* FINE SEZIONE NON CRITICA */

		/* 3: AGGIUNGERE VOSTRO CODICE PER GESTIRE L-USCITA
				DELLE MUCCHE DALLA MANGIATOIA
				ED EVENTUALMENTE PER SVEGLIARE L'ALLEVATORE */

		/* ora la mucca smette di mangiare. */
		/* riprendo la mutua esclusione */
		DBGpthread_mutex_lock(&mutex,Label); 
		/* io non mangio piu' */
		numMuccheCheMangiano--;
		/* sveglio l'allevatore se si vuole rifornire
		  ama solo se io ero l'ultima mucca che stava mangiando
		*/
		if( numMuccheCheMangiano==0 &&  AllevatoreVuoleRifornireOStaRifornendo>0 ) 
			DBGpthread_cond_broadcast ( &condAllevatore, Label );
		
		/* rilascio la mutua esclusione sulle variabili */
		DBGpthread_mutex_unlock(&mutex,Label); 


		/* 3: FINE AGGIUNTA VOSTRO CODICE PER FAR SMETTERE DI MANGIARE LE MUCCHE
				ED EVENTUALMENTE PER SVEGLIARE L'ALLEVATORE */

		/* fuori da mutua esclusione, mucca fa un giro, per un numero di secondi pari a 1+(indice%3) */
		tempogiro=5+(indice);
		printf("Mucca %s va a spasso per %i secondi \n", Label, tempogiro ); 
		fflush(stdout);
		DBGsleep(tempogiro,Label);
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_cond_init(&condAllevatore, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condMucche, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	numMuccheCheMangiano=0;	/* all'inizio nessun mucca sta mangiando */
	/* all'inizio la mangiatoia e' gia' piena di fieno */ 
	AllevatoreVuoleRifornireOStaRifornendo=0;	/* all'inizio nessun allevatore rifornisce la mangiatoia */

	for(i=0;i<NUMMUCCHE;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Mucca,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	/* ALLEVATORE */
	intptr=malloc(sizeof(int));
	if( !intptr ) { printf("malloc failed\n");exit(1); }
	*intptr=0;
	rc=pthread_create( &th,NULL,Allevatore,(void*)intptr); 
	if(rc) PrintERROR_andExit(rc,"pthread_create failed");

	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
