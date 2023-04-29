/* file:  LettoriScrittori_SenzaMutexAnnidate.c 
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
#include <stdint.h>	/* uint64_t */
#include <sys/time.h>	/* gettimeofday()    struct timeval */
#include <pthread.h> 
#include "printerror.h"
#include "DBGpthread.h"

/* commentato per fare meno output
#define FAIOUT
*/



#define NUMLETTORI 10
#define NUMSCRITTORI 5
/* 1/10 secondo */
#define SCRITTOREATTESAPRIMADISEZIONECRITICA  10000000
/* 1/10 secondo */
#define SCRITTOREATTESADENTROSEZIONECRITICA   10000000
/* 1/10 secondo */
#define LETTOREATTESADENTROSEZIONECRITICA     10000000
/* 1/10 secondo */
#define LETTOREATTESADOPOSEZIONECRITICA       10000000


/* Buffer da proteggere */
uint64_t valGlobale=0;
/* var da proteggere ma usate solo per fini statistici */
uint64_t numLettureTotali=0, numScrittureTotali=0;
/* var per controllo automatico */
char ultimoaccesso='0';

/* variabili per la sincronizzazione */
int numLettoriInLettura=0;  /* numero lettori che stanno leggendo */
int numScrittoriInScrittura=0;  /* numero scrittori che stanno scrivendo */
pthread_mutex_t  mutex; /* permette accesso a var condivise */
pthread_cond_t	cond_lettori, cond_scrittori;


void *Scrittore (void *arg) 
{ 
	char Slabel[128];
	int indice;
	uint64_t valProdotto=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Slabel,"S%d",indice);

	while(1) {
		/* QUI LO SCRITTORE PRODUCE IL DATO, un intero.
		   supponiamo che lo scrittore impieghi circa
		   1/10 di secondo a produrre il dato
		*/
		DBGnanosleep( SCRITTOREATTESAPRIMADISEZIONECRITICA ,Slabel);
#ifdef FAIOUT
		printf("Scrittore %s PRODUCE dato %lu\n", Slabel, valProdotto+1 ); 
		fflush(stdout);
#endif
		valProdotto++;
		
		/* prendo la mutua esclusione */
		DBGpthread_mutex_lock(&mutex,Slabel); 

		while(	numLettoriInLettura!=0 ||
			numScrittoriInScrittura!=0 ) 
			DBGpthread_cond_wait(&cond_scrittori,&mutex,Slabel);
		
		numScrittoriInScrittura++;
		DBGpthread_mutex_unlock(&mutex,Slabel); 

		/* INIZIO SEZIONE CRITICA NON PROTETTA DA LOCK */
		ultimoaccesso='W'; /* controllo automatizzato */
		/* riempie il buffer col dato prodotto prima */
		valGlobale=valProdotto;

		/* lo scrittore impiega un po' di tempo nella sezione critica, 1/10 sec */
		DBGnanosleep( SCRITTOREATTESADENTROSEZIONECRITICA, Slabel );

		printf("Scrittore %s inserisce %lu \n", Slabel, valGlobale ); 
		fflush(stdout);
		numScrittureTotali++; /* aggiorno statistiche */
		if( ultimoaccesso != 'W' ) {
			printf( "errore !w in scrittura \n");
			fflush(stdout);
			exit(1);
		}
		/* FINE SEZIONE CRITICA */

		DBGpthread_mutex_lock(&mutex,Slabel); 
		numScrittoriInScrittura--;
		/* avviso lettori e scrittori di ricontrollare la situazione */
		DBGpthread_cond_broadcast(&cond_scrittori,Slabel);
		DBGpthread_cond_broadcast(&cond_lettori,Slabel);
		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Slabel); 

	}
	pthread_exit(NULL); 
} 

void *Lettore (void *arg) 
{ 
	uint64_t val;
	char Llabel[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Llabel,"L%d",indice);

	
	while(1) {

		/* prendo la mutua esclusione */
		DBGpthread_mutex_lock(&mutex,Llabel); 

		while( numScrittoriInScrittura!=0 ) 
			DBGpthread_cond_wait(&cond_lettori,&mutex,Llabel);
		
		numLettoriInLettura++;
		DBGpthread_mutex_unlock(&mutex,Llabel); 

		/* SEZIONE CRITICA NON PROTETTA DA lock: leggo cio' che c'e' nel buffer */
		ultimoaccesso='R'; /* controllo automatizzato */
		val=valGlobale;
		numLettureTotali++; /* aggiorno statistiche */

		/* il lettore impiega un po' di tempo nella sezione critica 1/10 di sec */
		DBGnanosleep( LETTOREATTESADENTROSEZIONECRITICA , Llabel );

		printf("Lettore %s legge %lu \n", Llabel, val ); 
		fflush(stdout);

		if( ultimoaccesso != 'R' ) {
			printf( "errore !R in lettura \n");
			fflush(stdout);
			exit(1);
		}
		/* FINE SEZIONE CRITICA */

		DBGpthread_mutex_lock(&mutex,Llabel); 
		numLettoriInLettura--;
		if( numLettoriInLettura==0 ) {
			/* avviso scrittori di ricontrollare la situazione */
			DBGpthread_cond_broadcast(&cond_scrittori,Llabel);
		}
		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Llabel); 


		/* fuori da mutua esclusione, uso il dato 
		   supponiamo che ci voglia 1/10 secondo
		*/
#ifdef FAIOUT
		printf("Lettore %s USA dato %lu \n", Llabel, val ); 
		fflush(stdout);
#endif
		DBGnanosleep( LETTOREATTESADOPOSEZIONECRITICA , Llabel );
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;
	struct timeval t0, t1;

	DBGpthread_mutex_init(&mutex, NULL, "main"); 
	DBGpthread_cond_init(&cond_lettori, NULL, "main"); 
	DBGpthread_cond_init(&cond_scrittori, NULL, "main"); 

	gettimeofday( &t0, NULL );

	numLettoriInLettura=0;	 /* all'inizio nessun lettore sta leggendo */
	numScrittoriInScrittura=0; /* nesuno scrittore sta scrivendo */

	for(i=0;i<NUMLETTORI;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Lettore,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMSCRITTORI;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Scrittore,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	/* ora il main ogni 10 secondi stampa in output le statistiche di utilizzo
	   ok, e' un modo un po' alla cazzo di cane, perche' dovrei accedere alle variabili
	   contenenti le statistiche in mutua esclusione, ma cosi' cambierei il flusso
	   di esecuzione .....
	*/
	while(1) {
		DBGsleep( 10, "main" );
		gettimeofday( &t1, NULL );
		printf("******************* STATISTICHE D\'USO approssimate"
			" numLettureTotali %lu  numScrittureTot %lu NumLettureAlSecondo %f\n", 
			numLettureTotali, numScrittureTotali,
			((float)numLettureTotali) / (t1.tv_sec-t0.tv_sec) );
	}
	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
