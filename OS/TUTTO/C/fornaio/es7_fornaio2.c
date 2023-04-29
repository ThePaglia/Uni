/* file:  es7_fornaio2.c
	  i clienti accedono al bancone del pane
	  nell'ordine con cui hanno ottenuto il biglietto.
	  2 clienti possono stare contemporaneamente al bancone.
	  Ciascun cliente impiega circa 1 secondo a servirsi al bancone.
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


/* 
*/
#define DO_OUTPUT 1


#define NUMCLIENTI 50 

/* variabili da proteggere */
int64_t bigliettoGlob=0;
int64_t bigliettoSulDisplay=0;
int numeroClientiAlBancone=0;
/* variabili per la sincronizzazione */
pthread_mutex_t  mutexDistributoreBiglietti;
pthread_mutex_t  mutexTurno;
pthread_cond_t   condCambioTurno;

void *cliente (void *arg) 
{ 
	char Clabel[128];
	intptr_t indice;
	int64_t biglietto;

	indice=(intptr_t)arg;
	sprintf(Clabel,"C%" PRIiPTR "" , indice);

	while(1) 
	{
	
		/* il cliente aspetta qualche giorno = 0 sec e poi va dal fornaio */
	
		/* cliente prende il biglietto */
		/* prendo la mutua esclusione */
		DBGpthread_mutex_lock(&mutexDistributoreBiglietti,Clabel); 
		biglietto=bigliettoGlob;
		bigliettoGlob++;
		/* rilascio la mutua esclusione sul distributore */
		DBGpthread_mutex_unlock(&mutexDistributoreBiglietti,Clabel); 
	
#ifdef DO_OUTPUT
		printf("il cliente %s ha preso il biglietto %ld \n", Clabel, biglietto ); 
		fflush(stdout);
#endif

	
		/* cliente controlla ripetutamente il biglietto sul Display
		   per vedere se e' il suo turno oppure no.
		*/
		
		/* prendo la mutua esclusione */
		DBGpthread_mutex_lock(&mutexTurno,Clabel); 
		while( bigliettoSulDisplay != biglietto ) 
		{
			DBGpthread_cond_wait( &condCambioTurno, &mutexTurno,Clabel); 
		}

		numeroClientiAlBancone++;
		if( numeroClientiAlBancone<2 ) {
			bigliettoSulDisplay++;
			DBGpthread_cond_broadcast( &condCambioTurno, Clabel); 
		}
			
		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutexTurno,Clabel); 

		/* il cliente si serve al bancone */
#ifdef DO_OUTPUT
		printf("il cliente %s si serve al bancone: biglietto %ld  bigliettoSulDisplay %ld \n", Clabel, biglietto, bigliettoSulDisplay ); 
		fflush(stdout);
#endif
		/* il cliente impiega circa 1 secondo a servirsi */
		DBGnanosleep( 999999999-(10000000*indice), Clabel );
		/* DBGsleep( 1, Clabel ); */
	
		/* il cliente ha finito,
		   libera il posto
		   e definisce chi e' prossimo di turno 
		*/
		DBGpthread_mutex_lock(&mutexTurno,Clabel); 
		if( numeroClientiAlBancone==2 ) {
			bigliettoSulDisplay++;
			/* risveglia clienti in attesa cambio turno */
			DBGpthread_cond_broadcast( &condCambioTurno, Clabel); 
		}
		numeroClientiAlBancone--;

		/* e il cliente se ne va */
#ifdef DO_OUTPUT
		printf("il cliente %s se ne va \n", Clabel ); 
		fflush(stdout);
#endif
	
		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutexTurno,Clabel); 

	}

	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t   th; 
	int  rc;
	intptr_t i;

	rc = pthread_mutex_init( &mutexDistributoreBiglietti, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_mutex_init( &mutexTurno, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");
	rc = pthread_cond_init( &condCambioTurno, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	bigliettoGlob=0;
	bigliettoSulDisplay=0;
	numeroClientiAlBancone=0;
	
	/* lancio i clienti */
	for(i=0;i<NUMCLIENTI;i++) {
		rc=pthread_create( &th, NULL,cliente,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	while(1) {
	/* aspetto 1 minuto e guardo quanti clienti sono stati serviti */
		sleep(60);
		printf("num clienti serviti: %ld \n", bigliettoSulDisplay);
		fflush(stdout);
	}

	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
