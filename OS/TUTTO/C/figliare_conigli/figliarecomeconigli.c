/* file:  figliarecomeconigli.c */

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
#include <inttypes.h>
#include <pthread.h> 

#include "DBGpthread.h"

#define MAXNUMTHREAD 5

pthread_attr_t attr;

/* dati da proteggere */
pthread_mutex_t  mutex;
intptr_t counter=0; 
int	numthreads=0;

void *figlio (void *arg) 
{ 
	pthread_t    th; 
	int  rc;
	char Plabel[128];
	intptr_t indice, newIndice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"Th_%" PRIiPTR "",indice);

	DBGpthread_mutex_lock(&mutex,Plabel); 
	printf("%s %i\n", Plabel, numthreads );
	fflush(stdout);
	DBGpthread_mutex_unlock(&mutex,Plabel); 

	while(1) {
		DBGpthread_mutex_lock(&mutex,Plabel); 
		if( numthreads<MAXNUMTHREAD ) {
			newIndice=counter;
			counter++;
			numthreads++;

			rc=pthread_create(&th, &attr,figlio,(void*)newIndice); 
			if(rc) PrintERROR_andExit(rc,"pthread_create failed");
			DBGpthread_mutex_unlock(&mutex,Plabel); 
		}
		else {
			numthreads--;
			DBGpthread_mutex_unlock(&mutex,Plabel); 
			pthread_exit(NULL); 
		}
	}
	pthread_exit(NULL); 
} 

int main ( int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc;
	intptr_t newIndice;

	pthread_attr_init(&attr);
  pthread_attr_setdetachstate(&attr,PTHREAD_CREATE_DETACHED);

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	for(;;) {
		DBGpthread_mutex_lock(&mutex,"main"); 
		newIndice=counter;
		counter++;
		numthreads++;
		rc=pthread_create(&th,&attr,figlio,(void*)newIndice); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
		DBGpthread_mutex_unlock(&mutex,"main"); 

		sleep(5);
	}

	pthread_exit(NULL);
	return(0); 
} 
  
