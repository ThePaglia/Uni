/* file:  es35_masochistiasanzaccaria.c 
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
#include <stdint.h>	/* uint64_t  intptr_t */
#include <inttypes.h>	/* PRIiPTR */
#include <pthread.h> 
#include "printerror.h"
#include "DBGpthread.h"

/*
#define FAIOUT
*/

#define NUMLATI 2
#define NUMAUTOA 3
#define NUMAUTOB 4
#define TEMPOPERCORRENZA 2 /* secondi */
#define TEMPOATTRAVERSAMENTO 1 /* secondo */

/* variabili da proteggere */
int presente[NUMLATI];    /* c'e' un auto in attesa in quel lato */

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex;    /* mutex su variabili condivise */
pthread_cond_t   condAttesaAttraversamento; 
pthread_mutex_t  mutexPrimaPosizione[NUMLATI];    /* chi la supera ha la prima
						     posizione in quel lato
						     del ponte */
typedef struct parametri_categoria {
        int indice;
        char categoria;
} Parameter;

/* funzione che deve essere eseguita in mutua esclusione.
   Verifica se ci sono auto in prima posizione in tutti i lati del ponte.
*/
int CiSonoAutoAiDueLati(void) {
	int i, ris, trovateAuto=0;
	for( i=0; i<NUMLATI; i++ ) 
		if( presente[i])
			trovateAuto++;
	if(trovateAuto==NUMLATI) ris=1;
	else			 ris=0;
	return(ris);
}

void *Auto(void *arg) 
{ 
	char Alabel[128];
	char tipoauto;
	int indiceauto;
	int indicelatoattuale;

        indiceauto=((Parameter*)arg)->indice;
        tipoauto=((Parameter*)arg)->categoria;
        free(arg);

	printf("sono auto tipo %c indice %i\n", tipoauto, indiceauto);
	sprintf(Alabel,"auto%c%d",tipoauto,indiceauto);

	/* all'inizio ciascuna macchina A sta per arrivare al ponte
	   dal lato 0  e ciascuna macchina B dal lato 1 */ 
	if( tipoauto == 'A' )	
		indicelatoattuale=0;	
	else
		indicelatoattuale=1
	/* arrivo in indicevia attuale */
;	
	while(1) {
#ifdef FAIOUT
		printf("auto tipo %s si avvicina al ponte da lato %i\n", Alabel, indicelatoattuale ); 
		fflush(stdout);
#endif

		/* cerco di prendere la prima posizione nel lato del ponte */
		DBGpthread_mutex_lock(&mutexPrimaPosizione[indicelatoattuale],Alabel); 
		printf("auto %s e' in prima posizione in lato %i\n", Alabel, indicelatoattuale ); 
		fflush(stdout);

		/* sono in prima posizione nel mio lato del ponte, 
		   cerco di accedere alle variabili globali
		   per vedere se ci sono altre macchine presenti
		*/
		DBGpthread_mutex_lock(&mutex,Alabel); 
		presente[indicelatoattuale]=1;

		if( ! CiSonoAutoAiDueLati() ) {
			/* non posso proseguire */
			DBGpthread_cond_wait( &condAttesaAttraversamento, &mutex, Alabel);
			/* dall'altro lato mi hanno svegliato 
			   l'altro e' gia' passato
			*/
			printf("aaaaaaah!  %s attraversa da lato %i \n", Alabel, indicelatoattuale ); 
			fflush(stdout);
			/* non siamo piu' nelle prime posizioni all'incrocio */	
			presente[0]=0;
			presente[1]=0;
			DBGpthread_mutex_unlock(&mutex,Alabel); 
			DBGpthread_mutex_unlock(&mutexPrimaPosizione[0],Alabel); 
			DBGpthread_mutex_unlock(&mutexPrimaPosizione[1],Alabel); 
		} else {
			/* dico all'altro lato di passare */
			DBGpthread_cond_signal( &condAttesaAttraversamento, Alabel);
			DBGpthread_mutex_unlock(&mutex,Alabel); 
			printf("aaaaaaah!  %s attraversa da lato %i \n", Alabel, indicelatoattuale ); 
			fflush(stdout);
			/* ma fingo di essere ancora in prima posizione */
		}

		/* proseguo nella mia strada */
		if( indicelatoattuale==0 )
			indicelatoattuale=1;
		else
			indicelatoattuale=0;
		
		/* percorro l'anello */
		sleep(TEMPOPERCORRENZA);
		/* manca poco al ponte */

		if( indicelatoattuale==0 )
			indicelatoattuale=1;
		else
			indicelatoattuale=0;
		
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
	pthread_t    th; 
	int  rc;
	intptr_t i;

	DBGpthread_cond_init( &condAttesaAttraversamento,NULL,"main");
	for(i=0;i<NUMLATI;i++)
		DBGpthread_mutex_init( &(mutexPrimaPosizione[i]),NULL,"main");
	DBGpthread_mutex_init( &mutex, NULL, "main"); 

	for(i=0;i<NUMLATI;i++) {
		presente[i]=0;
	}

	for(i=0; i<NUMAUTOA; i++ ) {
                Parameter *ptr;
                ptr=(Parameter*)malloc(sizeof(Parameter));
                if( !ptr ) { printf("malloc failed\n");exit(1); }
                ptr->indice=i;
                ptr->categoria='A';
		rc=pthread_create( &th,NULL,Auto,(void*)ptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	for(i=0; i<NUMAUTOB; i++ ) {
                Parameter *ptr;
                ptr=(Parameter*)malloc(sizeof(Parameter));
                if( !ptr ) { printf("malloc failed\n");exit(1); }
                ptr->indice=i;
                ptr->categoria='B';
		rc=pthread_create( &th,NULL,Auto,(void*)ptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	pthread_exit(NULL); 
	return(0); 
} 
  
  
  
