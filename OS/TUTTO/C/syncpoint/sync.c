/* file:  sync.c */

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

#define NUMTHREADS 5

/* dati da proteggere */

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   cond;
int counter=0;

void *Thread (void *arg) 
{ 
	char Plabel[128];
	int indice;
	int turno;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"T%d",indice);

	sleep(2);

	DBGpthread_mutex_lock(&mutex, Plabel);
	turno=counter;
	counter++; 

	printf("Thread %s entra con turno %d \n", Plabel, turno ); 
	fflush(stdout);

	if (counter == NUMTHREADS) {
		/* l'ultimo arrivato ne sveglia uno e 
		   rimette contatore a zero
		*/
		DBGpthread_cond_signal (&cond, Plabel);
		counter=0; 
	}
	DBGpthread_cond_wait(&cond, &mutex, Plabel);

	while ( turno != counter ) {
		DBGpthread_cond_signal (&cond, Plabel);
		DBGpthread_cond_wait(&cond, &mutex, Plabel);
	}
	counter++;
	DBGpthread_cond_signal (&cond, Plabel);

	printf("Thread %s esce con turno %d \n", Plabel, turno ); 
	fflush(stdout);
 
	DBGpthread_mutex_unlock (&mutex, Plabel);

	pthread_exit(NULL); 
	return(NULL);
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc, i, *intptr;

	rc = pthread_cond_init(&cond, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");


	for(i=0;i<NUMTHREADS;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Thread,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 

	return(0); 
} 
  
