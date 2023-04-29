/* file:  strumenti_complicato.c */

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

#define NUMLAVORAZIONI1 2
#define NUMLAVORAZIONI2 2
#define NUMSTRUMENTI1 2
#define NUMSTRUMENTI2 4
#define NUMSTRUMENTI3 2
#define INDICES1	0
#define INDICES2	1
#define INDICES3	2

/* dati da proteggere */
pthread_mutex_t  mutex;
pthread_cond_t	condFinitaLavorazione;
int	strumentiDisponibili[3]={ NUMSTRUMENTI1, NUMSTRUMENTI2, NUMSTRUMENTI3 };

/*	SCELTA PER EVITARE DEADLOCK:
		PRENDO SUBITO SIN DALL'INIZIO
		TUTTI GLI STRUMENTI CHE MI SERVONO
*/
int strumentiSufficentiPerL1(void) 
{
	if(	strumentiDisponibili[INDICES1]>=1 &&
			strumentiDisponibili[INDICES2]>=1 &&
			strumentiDisponibili[INDICES3]>=1
	  )
		return 1;
	else
		return 0;
}

int strumentiSufficentiPerL2(void) 
{
	if( strumentiDisponibili[INDICES1]>=1 &&
			strumentiDisponibili[INDICES2]>=1 &&
			strumentiDisponibili[INDICES3]>=1 
		)
		return 1;
	else
		return 0;
}

void prendiStrumentiPerL1( void )
{
	strumentiDisponibili[INDICES1]--;
	strumentiDisponibili[INDICES2]--;
	strumentiDisponibili[INDICES3]--;
}

void prendiStrumentiPerL2( void )
{
	strumentiDisponibili[INDICES1]--;
	strumentiDisponibili[INDICES2]--;
	strumentiDisponibili[INDICES3]--;
}

void rilasciaStrumentiDiL1( void )
{
	strumentiDisponibili[INDICES1]++;
	strumentiDisponibili[INDICES2]++;
	strumentiDisponibili[INDICES3]++;
}

void rilasciaStrumentiDiL2( void )
{
	strumentiDisponibili[INDICES1]++;
	strumentiDisponibili[INDICES2]++;
	strumentiDisponibili[INDICES3]++;
}

void *Lavorazione1 (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"L1_%" PRIiPTR "",indice);

	while(1) {
		DBGpthread_mutex_lock(&mutex,Plabel); 
		while( !	strumentiSufficentiPerL1() ) {
			DBGpthread_cond_wait(&condFinitaLavorazione,&mutex,Plabel);
		}
		prendiStrumentiPerL1();
		printf("L1 %s procede \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		sleep(5);

		DBGpthread_mutex_lock(&mutex,Plabel); 
		rilasciaStrumentiDiL1();
		DBGpthread_cond_signal(&condFinitaLavorazione,Plabel); 
		printf("L1 %s finita \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		sleep(1);
	}
	pthread_exit(NULL); 
} 

void *Lavorazione2 (void *arg) 
{ 
	char Plabel[128];
	intptr_t indice;

	indice=(intptr_t)arg;
	sprintf(Plabel,"L2_%" PRIiPTR "",indice);

	while(1) {
		DBGpthread_mutex_lock(&mutex,Plabel); 
		while( !	strumentiSufficentiPerL2() ) {
			DBGpthread_cond_wait(&condFinitaLavorazione,&mutex,Plabel);
		}
		prendiStrumentiPerL2();
		printf("L2 %s procede \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		sleep(4);

		DBGpthread_mutex_lock(&mutex,Plabel); 
		rilasciaStrumentiDiL2();
		DBGpthread_cond_signal(&condFinitaLavorazione,Plabel); 
		printf("L2 %s finita \n", Plabel );
		fflush(stdout);
		DBGpthread_mutex_unlock(&mutex,Plabel); 

		sleep(1);
	}
	pthread_exit(NULL); 
} 

int main ( int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc;
	intptr_t i;

	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	rc = pthread_cond_init(&condFinitaLavorazione, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	for(i=0;i<NUMLAVORAZIONI1;i++) {
		rc=pthread_create(&th,NULL,Lavorazione1,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(i=0;i<NUMLAVORAZIONI2;i++) {
		rc=pthread_create(&th,NULL,Lavorazione2,(void*)i); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL);
	return(0); 
} 
  
