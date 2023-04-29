/* file:  es31_staffetta_4x400.c 
   Nodello che fornisce passaggi di testimone sicuro
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
#include <stdint.h>
#include <inttypes.h>
#include <pthread.h> 

#include "printerror.h"
#include "DBGpthread.h"

#define NUMATLETI 4

/* dati da proteggere */
uint64_t valGlobale=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t	 condSuccessivoAttendeTestimone[NUMATLETI]; 
pthread_cond_t	 condPrecedenteAttendeConfermaRicezione; 
pthread_cond_t	 condSuccessivoAttendeTestimoneMollato; 
int		 passatotestimonea[NUMATLETI]; 
int turn=0;

void corre( intptr_t indice) {
	printf("atleta %" PRIiPTR " fa giro di pista \n", indice ); 
	fflush(stdout);
	sleep(1);
}

void ricevetestimone( intptr_t indice) 
{
	char Label[100];
	sprintf(Label, "T%lu"PRIiPTR , (unsigned long int) indice );

	DBGpthread_mutex_lock(&mutex,Label); 

	/* se il precedente ha gia' fatto la signal allora
	   il nostro deve proseguire senza fare la wait.
	   Se turn==indice significa che il precedente
	   ha gia' appoggiato il testimone.
	   Non faccio un while ma un if perche' il precedente
	   sveglia esattamente il solo successivo
	*/
	if( turn != indice ) {
		printf("atleta %" PRIiPTR " attende testimone \n", indice); 
		fflush(stdout);

		DBGpthread_cond_wait( 
			&(condSuccessivoAttendeTestimone[indice]),
			&mutex,Label);
	}

	/* SEZIONE CRITICA: passaggio testimone */
	printf("atleta %" PRIiPTR " sente testimone in mano \n", indice ); 
	fflush(stdout);

	DBGpthread_cond_signal( 
		&condPrecedenteAttendeConfermaRicezione,Label);

	DBGpthread_cond_wait( 
		&condSuccessivoAttendeTestimoneMollato,
		&mutex,Label);

	printf("successivo %" PRIiPTR " ha definitivamente preso testimone \n", indice ); 
	fflush(stdout);

	/* rilascio mutua esclusione */
	DBGpthread_mutex_unlock(&mutex,Label); 
}

void consegnatestimone( intptr_t indice) 
{
	char Label[100];
	sprintf(Label, "T%lu"PRIiPTR , (unsigned long int) indice );

	DBGpthread_mutex_lock(&mutex,Label);

	/* stabilisco il prossimo */ 
	turn = (turn+1)%NUMATLETI;

	/* consegno il testimone esattamente al successivo */
	DBGpthread_cond_signal( 
		&(condSuccessivoAttendeTestimone[turn]),Label);

	/* attendo conferma ricezione */
	DBGpthread_cond_wait( 
		&condPrecedenteAttendeConfermaRicezione,
		&mutex,Label);
	/* mollo testimone e urlo al successivo di partire */
	DBGpthread_cond_signal( 
		&condSuccessivoAttendeTestimoneMollato,Label);

	/* rilascio mutua esclusione, cosi' il successivo parte */
	DBGpthread_mutex_unlock(&mutex,Label); 
}

void *Atleta (void *arg) 
{ 
	intptr_t indice;

	indice=(intptr_t)arg;
	if( indice == 0 ) {
		/* il primo atleta che fa il primo giro di pista
		   ha gia' il testimone in mano 
		   e non riceve da nessuno il testimone.
		*/ 
		printf("caso particolare, PRIMO atleta %" PRIiPTR " parte \n", indice ); 
		corre(indice);
		consegnatestimone(indice);
	}

	while(1) {
		ricevetestimone(indice);
		corre(indice);
		consegnatestimone(indice);
	}

	printf("atleta %" PRIiPTR " termina\n", indice); 
	fflush(stdout);
	pthread_exit(NULL); 
}

 
int main () 
{ 
	pthread_t    tid; 
	int  rc; intptr_t i;

	DBGpthread_mutex_init(&mutex, NULL, "main");
	for(i=0;i<NUMATLETI;i++)
		DBGpthread_cond_init(&(condSuccessivoAttendeTestimone[i]), NULL,"main");
	DBGpthread_cond_init(&condPrecedenteAttendeConfermaRicezione, NULL,"main");
	DBGpthread_cond_init(&condSuccessivoAttendeTestimoneMollato, NULL,"main");

	turn=0;

	for(i=0;i<NUMATLETI;i++) {
		rc = pthread_create(&tid, NULL, Atleta, (void*)i ); 
		if (rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 
	return(0); 
} 
  
  
