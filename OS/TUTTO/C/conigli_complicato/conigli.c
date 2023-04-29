
/*   conigli.c    */

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



#define NUMCONIGLIINIZIALE		5
#define NUMCONIGLIDACREARE		1


/* dati da proteggere */
int global_indice;  /* indice da assegnare ai pthread */

int		conigliDentroTana=0;
int		uscire=0;	/* 0 coniglio deve aspettare prima di uscire da tana,   1 puo' uscire da tana */
int		entrare=1;	/* 1 coniglio puo' entrare in tana,  0 deve aspettare */

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   condEntrare, 		/* per essere svegliati quando si puo' entrare in tana */
		 condAttendiAltro;	/* il primo coniglio entrato in tana attende in tana l'arrivo del secondo coniglio */

/* attributi dei thread da creare */
pthread_attr_t attr;



void *Coniglio (void *arg) 
{ 
	char Ulabel[128];
	int indice;

	pthread_t    th; 
	int  rc, i, *intptr;

	indice=*((int*)arg);
	free(arg);
	sprintf(Ulabel,"U%d",indice);


		DBGpthread_mutex_lock(&mutex,Ulabel); 

		while ( entrare==0 ) {
			printf("coniglio %s in attesa di entrare \n", Ulabel );
			fflush(stdout);
	
			DBGpthread_cond_wait( &condEntrare, &mutex, Ulabel );
		}

		/* coniglio e' entrato, 
		   incrementa il numero degli entrati
		*/
		conigliDentroTana ++;

		printf("coniglio %s entrato come %i \n", Ulabel, conigliDentroTana );
		fflush(stdout);
	

		/* se e' da solo aspetta l'altro */
		if( conigliDentroTana == 1 ) {

			printf("primo coniglio %s aspetta altro per accoppiarsi \n", Ulabel );
			fflush(stdout);
	
			DBGpthread_cond_wait( &condAttendiAltro, &mutex, Ulabel );

			printf("primo coniglio %s si e' accoppiato  .... \n", Ulabel );
			fflush(stdout);

		}

		/* se e' il secondo ad entrare allora
		   blocca l'ingresso,
		   si accoppiano,
		   e sveglia l'altro entrato prima di lui
		*/
		else { /* conigliDentroTana==2 */

			/* secondo coniglio blocca l'ingresso */	
			entrare = 0;

			printf("secondo coniglio %s libera l'altro \n", Ulabel );
			fflush(stdout);

			DBGpthread_cond_signal( &condAttendiAltro, Ulabel );

			printf("secondo coniglio %s si e' accoppiato  .... \n", Ulabel );
			fflush(stdout);

		}
	
		/* sto per uscire
		   decrementa il numero dei presenti in tana
		*/
		conigliDentroTana --;

		/* se e' l'ultimo dei presenti allora
		   libera l'ingresso 
		   e segnalo a quelli in attesa che possono entrare
		*/
		if( conigliDentroTana == 0 ) {

			/* il secondo coniglio che esce riapre l'accesso a quelli fuori */	
			entrare = 1;

			printf("coniglio %s dice a quelli fuori case che possono entrare \n", Ulabel );
			fflush(stdout);

			/* devo liberare almeno due conigli che erano in attesa fuori tana */

			DBGpthread_cond_signal( &condEntrare, Ulabel );
			DBGpthread_cond_signal( &condEntrare, Ulabel );
		}
		

		printf("coniglio %s ESCE \n", Ulabel );
		fflush(stdout);
	

		/* crea UN altri thread   coniglio 
		*/


		printf("coniglio %s crea nuovi thread \n", Ulabel );
		fflush(stdout);
	
		for(  i=0;  i<NUMCONIGLIDACREARE;   global_indice++, i++ ) {
			intptr=malloc(sizeof(int));
			if( !intptr ) { printf("malloc failed\n");exit(1); }
			*intptr=global_indice;
			rc=pthread_create( &th,&attr,Coniglio,(void*)intptr); 
			if(rc) PrintERROR_andExit(rc,"pthread_create failed");
		}

		printf("coniglio %s CREPA e rilascia mutua esclusione \n", Ulabel );
		fflush(stdout);
	

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&mutex,Ulabel); 


	pthread_exit(NULL); 
} 


int main (int argc, char* argv[] ) 
{ 
	char Mlabel[128]="main";
	pthread_t    th; 
	int  rc, *intptr;
	
	/*inizializzo strutture e  variabili per sincronizzazione*/
	rc = pthread_cond_init(&condEntrare, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condAttendiAltro, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* inizializzo attributi per thread detached */
	pthread_attr_init(&attr);
	pthread_attr_setdetachstate(&attr,PTHREAD_CREATE_DETACHED);

	/*creo conigli */
	DBGpthread_mutex_lock(&mutex,Mlabel); 

	for(  global_indice=0;  global_indice<NUMCONIGLIINIZIALE;  global_indice++  ) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { 
			printf("malloc failed\n");exit(1); 
		}
		*intptr = global_indice;
		rc=pthread_create( &th,&attr,Coniglio,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	DBGpthread_mutex_unlock(&mutex,Mlabel); 
	
	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
