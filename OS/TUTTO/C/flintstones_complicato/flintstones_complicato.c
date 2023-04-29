/* file:  flinstones_complicato.c */

/* messo prima perche' contiene define _POSIX_C_SOURCE */
#include "printerror.h"
#include "DBGpthread.h"

#include <unistd.h>   /* exit() etc */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>     /* per strerror_r  and  memset */
#include <sys/mman.h>  /* shm_* stuff, and mmap() */
#include <sys/types.h>
#include <signal.h>
#include <sys/time.h>	/* timeval{} for select() */
#include <time.h>	/* timespec{} for pselect() */
#include <limits.h>	/* for OPEN_MAX */
#include <errno.h>
#include <sys/wait.h>
#include <assert.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdint.h>
#include <inttypes.h>

#include <fcntl.h>
#include <sys/stat.h>

#define NUMCAVERNICOLI_A 2
#define NUMCAVERNICOLI_B 1
#define NUMPOSTI 2

typedef struct sharedBuffer {
	/* dati da proteggere */
	int NumPostiLiberi;
	int LatoASalire;
	int LatoBSalire;
	/* variabili per la sincronizzazione */
	pthread_mutex_t mutex;
	pthread_cond_t  condDinoArrivato,
			condPasseggeriScesi,
			condPasseggeriSaliti,
			condAttendiDinoLatoA,
			condAttendiDinoLatoB;
} SharedBuffer;
SharedBuffer *P;


void *Dinosauro (int indice ) 
{ 
	char Plabel[128];
	char lato='A';

	sprintf(Plabel,"Dino%i",indice);

	DBGpthread_mutex_lock(&P->mutex,Plabel); 
	while(1) {
		printf("Dino avvisa di salire da lato %c \n", lato );
		fflush(stdout);
		/* dino vuole ripartire, avvisa di salire 
		   quelli che stanno sul lato in cui sta la coda ora */
		if( lato=='A' ) {
		    P->LatoASalire=1;
		    DBGpthread_cond_broadcast(&P->condAttendiDinoLatoA,Plabel); 
		} else {
		    P->LatoBSalire=1;
		    DBGpthread_cond_broadcast(&P->condAttendiDinoLatoB,Plabel); 
		}
		P->NumPostiLiberi=NUMPOSTI;
		printf("lato %c: Dino dice salite\n", lato );
		fflush(stdout);

		DBGpthread_cond_wait(&P->condPasseggeriSaliti,&P->mutex,Plabel);
		P->LatoASalire=0;
		P->LatoBSalire=0;
		printf("Dino parte da lato %c \n", lato );
		fflush(stdout);
		DBGpthread_mutex_unlock(&P->mutex,Plabel); 

		/* parte e in 2 secondi e' dall'altra parte */
		sleep(2);
		if( lato=='A' ) lato='B';
		else		lato='A';
		
		DBGpthread_mutex_lock(&P->mutex,Plabel); 
		printf("Dino arriva in lato %c \n", lato );
		fflush(stdout);
		/* avvisa i passeggeri di scendere */
		DBGpthread_cond_broadcast(&P->condDinoArrivato,Plabel); 
		printf("lato %c: Dino dice scendete \n", lato );
		fflush(stdout);

		/* ora deve attendere che siano scesi */
		DBGpthread_cond_wait(&P->condPasseggeriScesi,&P->mutex,Plabel);
	}
	pthread_exit(NULL); 
} 

void *Cavernicolo (int indice, char lato ) 
{ 
	char Plabel[128];

	sprintf(Plabel,"C%i",indice);

	while(1) {

		DBGpthread_mutex_lock(&P->mutex,Plabel); 
		if( lato=='A' ) {
			while(	P->LatoASalire == 0  ||  P->NumPostiLiberi==0  )
				DBGpthread_cond_wait(&P->condAttendiDinoLatoA,&P->mutex,Plabel);
		}
		else /* lato=='B' */  {
			while(	P->LatoBSalire == 0  ||  P->NumPostiLiberi==0  )
				DBGpthread_cond_wait(&P->condAttendiDinoLatoB,&P->mutex,Plabel);
		}
		/* salgo */
		printf("cavericolo %s sale lato %c: \n", Plabel, lato );
		fflush(stdout);

		P->NumPostiLiberi--;
		if( P->NumPostiLiberi==0 ) /* avviso Dino di partire */
			DBGpthread_cond_signal(&P->condPasseggeriSaliti,Plabel);
	
		/* attendo fine trasporto */
		DBGpthread_cond_wait(&P->condDinoArrivato,&P->mutex,Plabel);
		/* mi hanno detto di scendere */
		P->NumPostiLiberi++;
		if( P->NumPostiLiberi==NUMPOSTI ) /*avviso Dino che tutti scesi*/
			DBGpthread_cond_signal(&P->condPasseggeriScesi,Plabel); 

		/* ho cambiato lato */
		if( lato=='A' ) lato='B';
		else		lato='A';

		printf("cavericolo %s sceso lato %c: \n", Plabel, lato );
		fflush(stdout);

		DBGpthread_mutex_unlock(&P->mutex,Plabel); 

		sleep(4); /* giretto */
		
		/* voglio andare dall'altro lato */
		
	}	

	pthread_exit(NULL); 
}


int main ( int argc, char* argv[] ) 
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
	
	P->NumPostiLiberi=NUMPOSTI;
	P->LatoASalire=0,
	P->LatoBSalire=0;

	rc=pthread_mutexattr_init(&mattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_init  failed");
	rc=pthread_mutexattr_setpshared(&mattr,PTHREAD_PROCESS_SHARED);
	if( rc ) PrintERROR_andExit(rc,"pthread_mutexattr_setpshared  failed");
	rc=pthread_condattr_init(&cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_condattr_init  failed");
	rc=pthread_condattr_setpshared(&cvattr,PTHREAD_PROCESS_SHARED);
	if( rc ) PrintERROR_andExit(rc,"pthread_condattr_setpshared  failed");


	rc = pthread_cond_init(&P->condDinoArrivato, &cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condPasseggeriScesi, &cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condPasseggeriSaliti, &cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condAttendiDinoLatoA, &cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&P->condAttendiDinoLatoB, &cvattr);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");

	rc = pthread_mutex_init(&P->mutex, &mattr); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");


	for(i=0;i<NUMCAVERNICOLI_A+NUMCAVERNICOLI_B;i++) {
		pid=fork();
		if(pid<0) 
			PrintERROR_andExit(errno,"fork failed");
		else if(pid==0) {
			if( i<NUMCAVERNICOLI_A )
				Cavernicolo(i,'A');
			else 
				Cavernicolo(i,'B');
			exit(0);
		}
	}
	Dinosauro(0); 
	
	return(0); 
} 
  
  
