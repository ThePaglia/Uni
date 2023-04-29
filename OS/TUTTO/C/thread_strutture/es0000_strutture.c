/* es0000_strutture.c  */

/* simboli gia' messi nella riga di comando del compilatore 
#define _THREAD_SAFE
#define _REENTRANT
#define _POSIX_C_SOURCE 200112L
*/

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>

#define NUM_THREADS 4

typedef struct struttura {
	int N;
	char Str[100];
	int Indice;
} Struttura;

void *Fai(void *ptr)
{
	Struttura *p, *p1;
	int t, rc;

	p=(Struttura*)ptr;
	
	printf("inizio thread N %i  Indice %i \n", p->N, p->Indice );
	fflush(stdout);
	sleep(1);

	if( p->N > 1 ) {
		pthread_t vth[NUM_THREADS];
		for(t=0; t<p->N-1; t++){
			p1=(Struttura*)malloc(sizeof(Struttura));
			if(p1==NULL) {
				perror("malloc failed: ");
				pthread_exit (NULL);
			}
			p1->N=p->N-1;
			p1->Indice=t;
			strcpy(p1->Str,"ciao");
			rc = pthread_create (&vth[t], NULL, Fai, p1 );
			if (rc) {
				printf("ERROR; return code from pthread_create() is %d\n",rc);
				exit(1);
			}
		}
		for(t=0; t<p->N-1; t++) {
			rc = pthread_join(vth[t], (void**)&p1 ); 
			if (rc) {
				printf("ERROR; return code from pthread_join() is %d\n",rc);
				exit(1);
			}
			printf("received \"%s\"\n", p1->Str);
			fflush(stdout);
			free(p1);
		}
	}
	/*
	printf("returning \n");
	fflush(stdout);
	*/
		
	sprintf( p->Str, "%i %i", p->N, p->Indice);
	pthread_exit (p);
}

int main()
{
	pthread_t vth[NUM_THREADS];
	int rc, t;
	Struttura *p;
	srand( time(NULL) );
	for(t=0; t<NUM_THREADS; t++){
		p=(Struttura*)malloc(sizeof(Struttura));
		if(p==NULL) {
			perror("malloc failed: ");
			pthread_exit (NULL);
		}
		p->N=NUM_THREADS-1;
		strcpy(p->Str,"ciao");
		p->Indice=t;
		printf("Creating thread %d\n", t);
		fflush(stdout);
		rc = pthread_create (&vth[t], NULL, Fai, p );
		if (rc) {
			printf("ERROR; return code from pthread_create() is %d\n",rc);
			exit(1);
		}
	}
	for(t=0;t<NUM_THREADS;t++) {
		rc = pthread_join(vth[t], (void**)&p ); 
		if (rc) {
			printf("ERROR; return code from pthread_join() is %d\n",rc);
			exit(1);
		}
		printf("main received \"%s':\n", p->Str);
		fflush(stdout);
		free(p);
	}
	
	pthread_exit (NULL);
	return(0);
}

