
/*    alieni.c    */

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



#define NUMALIENIINIZIALE		5
#define NUMMINIMOALIENIFUORICASA	3
#define NUMALIENIDACREARE		2


/* dati da proteggere */
int global_indice;  /* indice da assegnare ai pthread */

int		alieniFuoriCasa=0;
int		alieniDentroCasa=0;

/* variabili per la sincronizzazione */
pthread_mutex_t  mutex; 
pthread_cond_t   condEntrare, 		/* per essere svegliati quando si puo' entrare in casa */
		 condAttendiAltro;

/* attributi dei thread da creare */
pthread_attr_t attr;



void *Alieno (void *arg) 
{ 
	char Ulabel[128];
	int indice;

	pthread_t    th; 
	int  rc, i, *intptr;

	indice=*((int*)arg);
	free(arg);
	sprintf(Ulabel,"U%d",indice);


		DBGpthread_mutex_lock(&mutex,Ulabel); 

		/* alieno in attesa fuori casa */
		alieniFuoriCasa ++;

		while ( alieniDentroCasa >= 2 ) {
			printf("alieno %s in attesa di entrare \n", Ulabel );
			fflush(stdout);
	
			DBGpthread_cond_wait( &condEntrare, &mutex, Ulabel );
		}

		/* alieno e' entrato, 
		   incrementa il numero degli entrati
		   dencrementa il numero degli in attesa fuori
		*/
		alieniFuoriCasa --;
		alieniDentroCasa ++;

		printf("alieno %s entrato come %i \n", Ulabel, alieniDentroCasa );
		fflush(stdout);

		/* all'inizio il primo alieno trova la casa vuota, pazienza, spreco una signal */	

		/* normalmente c'e' gia' un alieno in casa e quello che entra e' il secondo */

		/* quindi dico al primo che puo' andare via */
		DBGpthread_cond_signal( &condAttendiAltro, Ulabel );

		/* e poi mi metto in attesa che qualche altro arrivi e mi svegli */
		DBGpthread_cond_wait( &condAttendiAltro, &mutex, Ulabel );

		/* ora sto per uscire, decremento il contatore degli alieni in casa e risveglio gli alieni fuori di casa */
		alieniDentroCasa --;

		printf("alieno %s dice a quelli fuori case che possono entrare \n", Ulabel );
		fflush(stdout);

		/* devo liberare un alieno di quelli che erano in attesa fuori casa */

		DBGpthread_cond_signal( &condEntrare, Ulabel );

		printf("alieno %s ESCE \n", Ulabel );
		fflush(stdout);

		/* se fuori gli alieni sono meno di 3 
		   allore crea altri due thread
		*/

		if( alieniFuoriCasa < NUMMINIMOALIENIFUORICASA ) {

			printf("alieno %s crea nuovi thread \n", Ulabel );
			fflush(stdout);
	
			for(  i=0;  i<NUMALIENIDACREARE;   global_indice++, i++ ) {
				intptr=malloc(sizeof(int));
				if( !intptr ) { printf("malloc failed\n");exit(1); }
				*intptr=global_indice;
				rc=pthread_create( &th,&attr,Alieno,(void*)intptr); 
				if(rc) PrintERROR_andExit(rc,"pthread_create failed");
			}
		}

		printf("alieno %s CREPA e rilascia mutua esclusione \n", Ulabel );
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

	/*creo alieni */
	DBGpthread_mutex_lock(&mutex,Mlabel); 

	for(  global_indice=0;  global_indice<NUMALIENIINIZIALE;  global_indice++  ) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { 
			printf("malloc failed\n");exit(1); 
		}
		*intptr = global_indice;
		rc=pthread_create( &th,&attr,Alieno,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
	DBGpthread_mutex_unlock(&mutex,Mlabel); 
	
	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
