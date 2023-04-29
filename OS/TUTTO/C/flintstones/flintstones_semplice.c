/* file:  flinstones.c */

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
#include <inttypes.h>
#include <pthread.h> 

#include "printerror.h"
#include "DBGpthread.h"

#define NUMCAVERNICOLI_A 2
#define NUMCAVERNICOLI_B 1
#define NUMPOSTI 2

/* dati da proteggere */
int NumPostiLiberi=NUMPOSTI;
int LatoASalire=0,
    LatoBSalire=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex;
pthread_cond_t   condDinoArrivato,
		 condPasseggeriScesi,
		 condPasseggeriSaliti,
		 condAttendiDinoLatoA,
		 condAttendiDinoLatoB;

void *Dinosauro (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;
	char lato='A';

	indice=(intptr_t)arg;
	sprintf(Plabel,"Dino%" PRIiPTR "",indice);

	DBGpthread_mutex_lock(&mutex,Plabel); 
	while(1) {
		printf("Dino avvisa di salire da lato %c \n", lato );
		fflush(stdout);
		/* dino vuole ripartire, avvisa di salire 
		   quelli che stanno sul lato in cui sta la coda ora */
		if( lato=='A' ) {
		    LatoASalire=1;
		    DBGpthread_cond_broadcast(&condAttendiDinoLatoA,Plabel); 
		} else {
		    LatoBSalire=1;
		    DBGpthread_cond_broadcast(&condAttendiDinoLatoB,Plabel); 
		}
		NumPostiLiberi=NUMPOSTI;
		printf("lato %c: Dino dice salite\n", lato );
		fflush(stdout);

		DBGpthread_cond_wait(&condPasseggeriSaliti,&mutex,Plabel);
		LatoASalire=0;
		LatoBSalire=0;
		printf("Dino parte da lato %c \n", lato );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		/* parte e in 2 secondi e' dall'altra parte */
		sleep(2);
		if( lato=='A' ) lato='B';
		else		lato='A';
		
		DBGpthread_mutex_lock(&mutex,Plabel); 
		printf("Dino arriva in lato %c \n", lato );
		fflush(stdout);
		/* avvisa i passeggeri di scendere */
		DBGpthread_cond_broadcast(&condDinoArrivato,Plabel); 
		printf("lato %c: Dino dice scendete \n", lato );
		fflush(stdout);

		/* ora deve attendere che siano scesi */
		DBGpthread_cond_wait(&condPasseggeriScesi,&mutex,Plabel);
	}
	pthread_exit(NULL); 
} 

void *Cavernicolo (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;
	char lato;

	indice=(intptr_t)arg;
	sprintf(Plabel,"C%" PRIiPTR "",indice);

	if( indice<NUMCAVERNICOLI_A )	lato='A';
	else				lato='B';


	while(1) {

		DBGpthread_mutex_lock(&mutex,Plabel); 
		if( lato=='A' ) {
			while(	LatoASalire == 0  ||  NumPostiLiberi==0  )
				DBGpthread_cond_wait(&condAttendiDinoLatoA,&mutex,Plabel);
		}
		else /* lato=='B' */  {
			while(	LatoBSalire == 0  ||  NumPostiLiberi==0  )
				DBGpthread_cond_wait(&condAttendiDinoLatoB,&mutex,Plabel);
		}
		/* salgo */
		printf("cavericolo %s sale lato %c: \n", Plabel, lato );
		fflush(stdout);

		NumPostiLiberi--;
		if( NumPostiLiberi==0 ) /* avviso Dino di partire */
			DBGpthread_cond_signal(&condPasseggeriSaliti,Plabel);
	
		/* attendo fine trasporto */
		DBGpthread_cond_wait(&condDinoArrivato,&mutex,Plabel);
		/* mi hanno detto di scendere */
		NumPostiLiberi++;
		if( NumPostiLiberi==NUMPOSTI ) /*avviso Dino che tutti scesi*/
			DBGpthread_cond_signal(&condPasseggeriScesi,Plabel); 

		/* ho cambiato lato */
		if( lato=='A' ) lato='B';
		else		lato='A';

		printf("cavericolo %s sceso lato %c: \n", Plabel, lato );
		fflush(stdout);

		DBGpthread_mutex_unlock(&mutex,Plabel); 

		sleep(4); /* giretto */
		
		/* voglio andare dall'altro lato */
		
	}	

	pthread_exit(NULL); 
}


int main ( int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc;
	intptr_t i;

	rc = pthread_cond_init(&condDinoArrivato, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condPasseggeriScesi, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condPasseggeriSaliti, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condAttendiDinoLatoA, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condAttendiDinoLatoB, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");


	for(i=0;i<NUMCAVERNICOLI_A+NUMCAVERNICOLI_B;i++) {
		rc=pthread_create(&th,NULL,Cavernicolo,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	i=0;
	rc=pthread_create(&th,NULL,Dinosauro,(void*)i); 
	if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	
	pthread_exit(NULL);
	return(0); 
} 
  
  
  
