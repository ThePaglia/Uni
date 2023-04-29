/* piantarepalicomplicato.c */

#ifndef _THREAD_SAFE
	#define _THREAD_SAFE
#endif
#ifndef _POSIX_C_SOURCE
	#define _POSIX_C_SOURCE 200112L
#endif

#include "printerror.h"

#include <unistd.h>   /* exit() etc */
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>     /* per strerror_r  and  memset */
#include <sys/mman.h>  /* shm_* stuff, and mmap() */
#include <sys/types.h>
#include <signal.h>
#include <sys/time.h> /* timeval{} for select() */
#include <time.h> /* timespec{} for pselect() */
#include <limits.h> /* for OPEN_MAX */
#include <errno.h>
#include <sys/wait.h>
#include <assert.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdint.h>
#include <inttypes.h>
#include <fcntl.h>
#include <sys/stat.h>

#include <pthread.h> 

#include "DBGpthread.h"


#define NUMMARTELLI 2
#define NUMPALI 2


/* dati da proteggere */
typedef struct sharedBuffer {
		int numMartelliInAzione;
		int numMartelliFinito;
		int haMartellato[NUMMARTELLI];
		int MartelliMartellate;	/* martelli possono iniziare a martellare */
		/* variabili per la sincronizzazione */
		pthread_mutex_t  mutex; 
		pthread_cond_t   condMartelliMartellate, condMartelliFinito;
} SharedBuffer;
SharedBuffer *P;


void Martello (int indice) 
{ 
	char label[128];
	int loopcounter=0;

	sprintf(label,"M%d",indice);
	
	while(1) {
		DBGpthread_mutex_lock(&P->mutex,label); 

		while( P->MartelliMartellate==0 || P->haMartellato[indice]==1 ) {

			printf("martello %s attende \n",label);
			fflush(stdout);

			DBGpthread_cond_wait(&P->condMartelliMartellate,&P->mutex,label);
		}

		loopcounter++;

		printf("martello %s martella \n", label);
		fflush(stdout);

		P->numMartelliInAzione++;
		P->haMartellato[indice]=1;

		if( P->numMartelliInAzione==NUMPALI ) 
			P->MartelliMartellate=0;

		DBGpthread_mutex_unlock(&P->mutex,label); 

		sleep(1);

		DBGpthread_mutex_lock(&P->mutex,label); 

		printf("martello %s finisce di martellare \n", label);
		fflush(stdout);

		P->numMartelliFinito++;
		if( P->numMartelliFinito==NUMPALI )
			DBGpthread_cond_signal(&P->condMartelliFinito, label);

		DBGpthread_mutex_unlock(&P->mutex,label); 

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


void TienePalo (int indice) 
{ 
	
	char label[128];
	
	int i;
	int loopcounter=0;

	sprintf(label,"T%d",indice);
	
	while(1) {
		printf("TienePalo %s prende pali\n", label);
		fflush(stdout);
		sleep(1);

		loopcounter++;

		DBGpthread_mutex_lock(&P->mutex,label); 
		printf("TienePalo %s ordina:  martellare!\n", label);
		fflush(stdout);

		for(i=0;i<NUMMARTELLI;i++) P->haMartellato[i]=0;
		P->numMartelliInAzione=0;
		P->numMartelliFinito=0;
		P->MartelliMartellate=1;
		DBGpthread_cond_broadcast(&P->condMartelliMartellate,label);
		DBGpthread_cond_wait(&P->condMartelliFinito,&P->mutex,label);

		printf("Tiene Palo %s lascia pali\n", label);
		fflush(stdout);
		
		DBGpthread_mutex_unlock(&P->mutex,label); 

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
	int shmfd, rc, i;
	pid_t pid;
	int shared_seg_size = sizeof(SharedBuffer);
	pthread_mutexattr_t mattr;
	pthread_condattr_t cvattr;

	shmfd = shm_open( "/pedala", O_CREAT /*| O_EXCL*/ | O_RDWR, S_IRWXU );
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

	P = (SharedBuffer*)mmap(NULL, shared_seg_size,
		PROT_READ|PROT_WRITE, MAP_SHARED, shmfd, 0);
	if( P==MAP_FAILED )
	 		PrintERROR_andExit(errno,"mmap  failed");

	rc=pthread_mutexattr_init(&mattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_init  failed");
	rc=pthread_mutexattr_setpshared(&mattr,PTHREAD_PROCESS_SHARED);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_setpshared  failed");
	rc=pthread_condattr_init(&cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_condattr_init  failed");
	rc=pthread_condattr_setpshared(&cvattr,PTHREAD_PROCESS_SHARED);
	if( rc ) PrintERROR_andExit(rc,"pthread_condattr_setpshared  failed");

	/*inizializzo strutture e  variabili per sincronizzazione*/
	rc = pthread_cond_init(&P->condMartelliMartellate, &cvattr );
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condMartelliFinito, &cvattr );
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_mutex_init(&P->mutex, &mattr ); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	/* inizializzo variabili globali da proteggere*/
	for(i=0;i<NUMMARTELLI;i++) P->haMartellato[i]=0;
	P->numMartelliInAzione=0;
	P->numMartelliFinito=0;
	P->MartelliMartellate=0;

	/*creo i martelli */
	for(i=0;i<NUMMARTELLI;i++) {
		pid=fork();
		if(pid<0) 
			PrintERROR_andExit(errno,"fork failed");
		else if(pid==0) {
			Martello(i);
			exit(0);
		}
	}
	TienePalo(0);
	return(0); 
} 
 
 
