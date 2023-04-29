/* piantarepalisemplice.c */

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#endif

#include "printerror.h"

#include <unistd.h> 
#include <stdlib.h> 
#include <stdio.h> 
#include <stdint.h>
#include <pthread.h> 

#include "DBGpthread.h"



#define NUMMARTELLI 2
#define NUMPALI 2


/* dati da proteggere */
int numMartelliInAzione=0;
int numMartelliFinito=0;
int haMartellato[NUMMARTELLI];
int MartelliMartellate=0;	/* i martelli possono iniziare a martellare */

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   condMartelliMartellate, condMartelliFinito;


void *Martello (void *arg) 
{ 
	char label[128];
	int indice;
	int loopcounter=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(label,"M%d",indice);
	
	while(1) {
		DBGpthread_mutex_lock(&mutex,label); 

		while( MartelliMartellate==0 || haMartellato[indice]==1 ) {

			printf("martello %s attende \n",label);
			fflush(stdout);

			DBGpthread_cond_wait(&condMartelliMartellate,&mutex,label);
		}

		loopcounter++;

		printf("martello %s martella \n", label);
		fflush(stdout);

		numMartelliInAzione++;
		haMartellato[indice]=1;

		if( numMartelliInAzione==NUMPALI ) 
			MartelliMartellate=0;

		DBGpthread_mutex_unlock(&mutex,label); 

		sleep(1);

		DBGpthread_mutex_lock(&mutex,label); 

		printf("martello %s finisce di martellare \n", label);
		fflush(stdout);

		numMartelliFinito++;
		if( numMartelliFinito==NUMPALI )
			DBGpthread_cond_signal(&condMartelliFinito, label);

		DBGpthread_mutex_unlock(&mutex,label); 

		if( loopcounter%4 ==0 ) {
			printf("martello %s si riposa\n", label);
			fflush(stdout);
			if(indice==0)
				sleep(5);
			else if(indice==1)
				sleep(7);
			printf("martello %s finisce riposo\n", label);
			fflush(stdout);
		}

	}
	pthread_exit(NULL); 
} 


void *TienePalo (void *arg) 
{ 
	
	char label[128];
	
	int indice,i;
	int loopcounter=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(label,"T%d",indice);
	
	while(1) {
		printf("TienePalo %s prende pali\n", label);
		fflush(stdout);
		sleep(1);

		loopcounter++;

		DBGpthread_mutex_lock(&mutex,label); 
		printf("TienePalo %s ordina:  martellare!\n", label);
		fflush(stdout);

		for(i=0;i<NUMMARTELLI;i++) haMartellato[i]=0;
		numMartelliInAzione=0;
		numMartelliFinito=0;
		MartelliMartellate=1;
		DBGpthread_cond_broadcast(&condMartelliMartellate,label);
		DBGpthread_cond_wait(&condMartelliFinito,&mutex,label);

		printf("Tiene Palo %s lascia pali\n", label);
		fflush(stdout);
		
		DBGpthread_mutex_unlock(&mutex,label); 

		if( loopcounter%5 ==0 ) {
			printf("TienePalo %s si riposa\n", label);
			fflush(stdout);
			sleep(3);
			printf("TienePalo %s finisce riposo\n", label);
			fflush(stdout);
		}
	}
	pthread_exit(NULL); 
}

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;
	
	/*inizializzo strutture e  variabili per sincronizzazione*/
	rc = pthread_cond_init(&condMartelliMartellate, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&condMartelliFinito, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* inizializzo variabili globali da proteggere*/
	numMartelliInAzione=0;
	numMartelliFinito=0;
	MartelliMartellate=0;

	/*creo i martelli */
	for(i=0;i<NUMMARTELLI;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Martello,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	
	/*creo tienepalo*/
	intptr=malloc(sizeof(int));
	if( !intptr ) { printf("malloc failed\n");exit(1); }
	*intptr=0;
	rc=pthread_create( &th,NULL,TienePalo,(void*)intptr); 
	if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	
	
	pthread_exit(NULL); 

	return(0); 
} 
 
 
