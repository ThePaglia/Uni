/* file:  ProcessiThread_NProdMCons1buffer_NONefficente.c 
   - NProduttori e NConsumatori che si scambiano prodotti
   mediante un unico Buffer. E' la versione NON efficente
   - I Produttori stanno in un processo.
   - I Consumatori stanno in un processo diverso.
   - I due processi non sono padre  e figlio.
   - Eseguendo il programma devo passare a riga di comando
   un carattere P o C per eseguire il ruolo di Produttori
   oppure il ruolo di Consumatori.
   - Uno script LANCIA.sh lancia in background  entrambi i processi,
   prima lancia il processo che crea i thread Consumatori,
   poi aspetta 2 secondi,
   poi lancia il processo che crea i thread Produttori.
   - Lo script aspetta 10 secondi e poi killa entrambi i processi
*/ 

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

#include <string.h>     /* per strerror_r  and  memset */
#include <sys/mman.h>  /* Memory MANagement: shm_* stuff, and mmap() */
#include <sys/types.h>
#include <signal.h>
#include <sys/time.h>	/* timeval{} for select() */
#include <time.h>	/* timespec{} for pselect() */
#include <limits.h>	/* for OPEN_MAX */
#include <errno.h>
#include <sys/wait.h>
#include <assert.h>
#include <fcntl.h>
#include <sys/stat.h>

#include <pthread.h> 

#include "printerror.h"
#include "DBGpthread.h"

#define NUMPROD 5
#define NUMCONS 3 
#define NUMBUFFER 1	/* NON MODIFICARE MAI */

typedef struct { 
   /* dati da proteggere */
	uint64_t valGlobale;
   /* variabili per la sincronizzazione */
	pthread_mutex_t	mutex; 
	pthread_cond_t	condProd;
	pthread_cond_t	condCons; 
	int numBufferPieni; /* 0 o 1 */
} buffer_t;
buffer_t *buffer;

void *Produttore (void *arg) 
{ 
	char Plabel[128];
	char Plabelsignal[128];
	int indice;
	uint64_t valProdotto=0;

	indice=*((int*)arg);
	free(arg);
	sprintf(Plabel,"P%d",indice);
	sprintf(Plabelsignal,"P%d->C",indice);

	while(1) {
		/* QUI IL PRODUTTORE PRODUCE IL DATO, un intero.
		   supponiamo che il produttore impieghi circa
		   un secondo a produrre il dato
		*/
		DBGsleep(1,Plabel);
		printf("Prod %s PRODUCE dato %lu\n", Plabel, valProdotto+1 ); 
		fflush(stdout);
		valProdotto++;
		
		DBGpthread_mutex_lock(&buffer->mutex,Plabel); 

		while ( buffer->numBufferPieni >= NUMBUFFER )
			DBGpthread_cond_wait(&buffer->condProd,&buffer->mutex,Plabel);

		/* riempie il buffer col dato prodotto prima
		   in realta' per semplicita' incremento la var 
		*/
		buffer->valGlobale=valProdotto;

		printf("Prod %s inserisce %lu \n", Plabel, buffer->valGlobale ); 
		fflush(stdout);

		buffer->numBufferPieni++;

		/* risveglio un Consumatore per svuotare un buffer */
		DBGpthread_cond_signal(&buffer->condCons,Plabelsignal); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&buffer->mutex,Plabel); 
	}
	pthread_exit(NULL); 
} 

void *Consumatore (void *arg) 
{ 
	uint64_t val;
	char Clabel[128];
	char Clabelsignal[128];
	int indice;

	indice=*((int*)arg);
	free(arg);
	sprintf(Clabel,"C%d",indice);
	sprintf(Clabelsignal,"C%d->P",indice);

	
	while(1) {
		DBGpthread_mutex_lock(&buffer->mutex,Clabel); 

		while( buffer->numBufferPieni <= 0 ) 
			DBGpthread_cond_wait(&buffer->condCons,&buffer->mutex,Clabel);

		/* prendo cio' che c'e' nel buffer */
		val=buffer->valGlobale;

		printf("Cons %s legge %lu \n", Clabel, val ); 
		fflush(stdout);

		buffer->numBufferPieni--;

		DBGpthread_cond_signal(&buffer->condProd,Clabelsignal); 

		/* rilascio mutua esclusione */
		DBGpthread_mutex_unlock(&buffer->mutex,Clabel); 

		/* fuori da mutua esclusione, uso il dato 
		   supponiamo che ci voglia 1 secondo
		*/
		DBGsleep(1,Clabel);
		printf("Cons %s USA dato %lu \n", Clabel, val ); 
		fflush(stdout);
	}
	pthread_exit(NULL); 
} 

int main (int argc, char* argv[] ) 
{ 
 pthread_t    th; 
 int  rc, i, *intptr;
 int shmfd;
 int shared_seg_size = sizeof(buffer_t);
 pthread_mutexattr_t mattr;
 pthread_condattr_t cvattr;
 char role;

 if(argc!=2) { printf("serve un argomento P o C\n"); exit(1); }
 else if( strcmp(argv[1],"P")==0) role='P';
 else if( strcmp(argv[1],"C")==0) role='C';
 else { printf("serve un argomento P o C\n"); exit(1); }

 if(role=='C') { /* Consumatori */	

    shmfd = shm_open( "/prodcons", O_CREAT | O_EXCL | O_RDWR, S_IRWXU );
    if (shmfd < 0) {
        perror("In shm_open()");
        exit(1);
    }
    /* adjusting mapped file size (make room for the whole segment to map) */
    rc = ftruncate(shmfd, shared_seg_size);
    if (rc != 0) {
        perror("ftruncate() failed");
        exit(1);
    }

    buffer = (buffer_t *)mmap(NULL, sizeof(buffer_t),
        PROT_READ|PROT_WRITE, MAP_SHARED, shmfd, 0);

    rc=pthread_mutexattr_init(&mattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_init  failed");
    rc=pthread_mutexattr_setpshared(&mattr,PTHREAD_PROCESS_SHARED);
    if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_setpshared  failed");
    rc=pthread_mutex_init(&buffer->mutex, &mattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init  failed");

    rc=pthread_condattr_init(&cvattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_condattr_init  failed");
    rc=pthread_condattr_setpshared(&cvattr,PTHREAD_PROCESS_SHARED);
    if( rc ) PrintERROR_andExit(rc,"pthread_condattr_setpshared  failed");
    rc=pthread_cond_init(&buffer->condCons, &cvattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_cond_init  failed");
    rc=pthread_cond_init(&buffer->condProd, &cvattr);
    if( rc ) PrintERROR_andExit(rc,"pthread_cond_init  failed");

    rc=pthread_condattr_destroy(&cvattr);
    rc=pthread_mutexattr_destroy(&mattr);

	/* all'inizio i Cons devono aspettare il primo Prod */
	buffer->numBufferPieni=0; /* 0 o 1 */
	buffer->valGlobale=0;

	for(i=0;i<NUMCONS;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Consumatore,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
}
else if(role=='P') { /* Produttori */	

    sleep(2);
    shmfd = shm_open( "/prodcons", O_RDWR, S_IRWXU );
    if (shmfd < 0) {
        perror("Prod failed in shm_open()");
        exit(1);
    }

    buffer = (buffer_t *)mmap(NULL, sizeof(buffer_t),
        PROT_READ|PROT_WRITE, MAP_SHARED, shmfd, 0);

	for(i=0;i<NUMPROD;i++) {
		intptr=malloc(sizeof(int));
		if( !intptr ) { printf("malloc failed\n");exit(1); }
		*intptr=i;
		rc=pthread_create( &th,NULL,Produttore,(void*)intptr); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}
}
	pthread_exit(NULL); 

	return(0); 
} 
  
  
  
