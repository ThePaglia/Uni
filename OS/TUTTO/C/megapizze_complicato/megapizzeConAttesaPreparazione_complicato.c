/* file:  megapizzeConAttesaPreparazione.c 
*/ 

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#endif

/* compilare con -D_BSD_SOURCE  per random */

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>	/* uint64_t */
#include <sys/time.h>	/* gettimeofday()    struct timeval */
#include <pthread.h> 
#include "printerror.h"
#include "DBGpthread.h"

#define NUMPERSONE 5
#define NUMPOSTI 4	/* posti a tavola */

/* stati dei tavoli */
#define TAVOLOLIBERO	0
#define	TAVOLOOCCUPATO	1
#define	PIZZARICHIESTA	2
#define	PIZZAARRIVATA	3
#define SIMANGIA	4	/* unico stato in cui sicuramente non possono sedersi altri clienti */

/* variabili per la sincronizzazione */
int		statotavolo;
int		postioccupati;
pthread_cond_t	condAttesaTavoloLibero;
pthread_cond_t	condAttesaMangiare;
pthread_mutex_t mutex; 
pthread_cond_t 	condPizzaioloChiamato; 
int		pizzaiolochiamato=0;		/* 0 non chiamato, >0 chiamato */

/* da chiamare in mutua esclusione */
int PossonoMangiare( void ) {
	if(	statotavolo==PIZZAARRIVATA	&&      postioccupati==NUMPOSTI )
		return(1);
	else
		return(0);
}
		
/* da chiamare NON in mutua esclusione */
int CONATTESAPREPARAZIONE_PreparaPortaPizzeEAvvisa( char *Plabel ) {
	/* prepara pizza */
	DBGsleep( 1, Plabel );

	DBGpthread_mutex_lock( &mutex, Plabel );
	statotavolo=PIZZAARRIVATA;
	pizzaiolochiamato=0;
	if( PossonoMangiare() ) 
		DBGpthread_cond_broadcast( &condAttesaMangiare, Plabel );

	printf("pizzaiolo ha portato pizza\n");
	fflush(stdout);

	DBGpthread_mutex_unlock( &mutex, Plabel );

	return(1);
}
	
void *CONATTESAPREPARAZIONE_Pizzaiolo (void *arg) 
{ 
	char Plabel[128];
	char Plabelsignal[128];

	sprintf(Plabel,"P");
	sprintf(Plabelsignal,"P->");

	while(1) {

		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock( &mutex, Plabel );
		if( pizzaiolochiamato > 0 ) {
			DBGpthread_mutex_unlock( &mutex, Plabel );
			CONATTESAPREPARAZIONE_PreparaPortaPizzeEAvvisa( Plabel );	/* qui faccio unlock mentre preparo e lock dopo */
			/* qui dentro termino con una unlock */
		}
		else {
			DBGpthread_cond_wait( &condPizzaioloChiamato, &mutex, Plabel );
			DBGpthread_mutex_unlock( &mutex, Plabel );
		}
	}
	pthread_exit(NULL);
}

void stampastatotavoli( void) {
	printf("statotavolo %d  postioccupati %d  pizzaiolochiamato %d \n",
		statotavolo, postioccupati, pizzaiolochiamato );
}

void *Cliente (void *arg) 
{ 
	char Clabel[128];
	char Clabelsignal[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Clabel,"C%d",indice);
	sprintf(Clabelsignal,"C%d->",indice);

	while(1) {

		/* prendo la mutua esclusione sulle variabili */
		DBGpthread_mutex_lock( &mutex, Clabel );

		stampastatotavoli();

		printf("cliente %s cerca tavolo con un posto libero\n", Clabel ); 
		fflush(stdout);

		/* if (	postioccupati<NUMPOSTI  &&  statotavolo != SIMANGIA ) */
		if (	postioccupati<NUMPOSTI  
			&&  
			(
				statotavolo == TAVOLOLIBERO
				||
				statotavolo == TAVOLOOCCUPATO
				||
				statotavolo == PIZZARICHIESTA
				||
				statotavolo == PIZZAARRIVATA
			)
		   )
		{
			printf("cliente %s trovato posto libero in tavolo \n", Clabel ); 
			fflush(stdout);

			/* mi aggiungo al tavolo */
		 	postioccupati++;

			/* se sono il primo arrivato occupo il tavolo */
			if( postioccupati == 1 ) {
				statotavolo=TAVOLOOCCUPATO;
				/* mi metto in attesa di mangiare */
				DBGpthread_cond_wait( &condAttesaMangiare, &mutex, Clabel );
			}

			/* se sono il terzo arrivato chiamo il pizzaiolo */
			else if( postioccupati == 3 ) {
				pizzaiolochiamato++;
				statotavolo=PIZZARICHIESTA;
				DBGpthread_cond_signal( &condPizzaioloChiamato, Clabel );
				/* mi metto in attesa di mangiare */
				DBGpthread_cond_wait( &condAttesaMangiare, &mutex, Clabel );
			}

			/* se sono il quarto arrivato (tutti i posti occupati) e c'e' gia' la pizza
			   allora si deve cominciare a mangiare tutti
			*/
		 	else if ( postioccupati == NUMPOSTI && statotavolo==PIZZAARRIVATA ) {
				 DBGpthread_cond_broadcast( &condAttesaMangiare, Clabel );
			}

			/* se invece manca la pizza o manca qualcuno, si aspetta di mangiare */
			else {
				 DBGpthread_cond_wait( &condAttesaMangiare, &mutex, Clabel );
			}

			/* quando arrivo qui, posso mangiare */
			statotavolo=SIMANGIA;
			DBGpthread_mutex_unlock( &mutex, Clabel );

			printf("cliente %s mangia \n", Clabel ); 
			fflush(stdout);

			DBGsleep( 3 , Clabel );
	
			/* ora si deve uscire */	
			DBGpthread_mutex_lock( &mutex, Clabel );

			/* mi alzo dal tavolo */
			postioccupati--;
			/* se si sono alzati tutti ed io sono l'ultimo, 
			   libero il tavolo 
			   e risveglio chi aspettava
			*/
			if( postioccupati == 0 )  {
				statotavolo=TAVOLOLIBERO;
				DBGpthread_cond_broadcast( &condAttesaTavoloLibero, Clabel );
			}

			printf("cliente %s va via \n", Clabel ); 
			fflush(stdout);

			DBGpthread_mutex_unlock( &mutex, Clabel );
			
			/* cliente va in giro per 4 secondi */
			DBGsleep( 2+indice ,Clabel);

			/* torno al while */
		} 
		else {
			/* nessun posto libero, devo aspettare */

			printf("cliente %s deve aspettare \n", Clabel ); 
			fflush(stdout);

			DBGpthread_cond_wait( &condAttesaTavoloLibero, &mutex, Clabel );
			DBGpthread_mutex_unlock( &mutex, Clabel );
		}

	}
	pthread_exit(NULL); 
} 


int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	rc = pthread_cond_init( &condPizzaioloChiamato, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_cond_init( &condAttesaTavoloLibero, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_cond_init( &condAttesaMangiare, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	statotavolo=TAVOLOLIBERO;
	postioccupati=0;

	/* creo pizzaiolo */
	intptr=malloc(sizeof(int));
	if( !intptr ) { printf("malloc failed\n");exit(1); }
	*intptr=0;
	rc=pthread_create( &th,NULL,CONATTESAPREPARAZIONE_Pizzaiolo,(void*)intptr); 
	if(rc) PrintERROR_andExit(rc,"pthread_create failed");

	/* creo clienti */
	for(i=0;i<NUMPERSONE;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Cliente,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
