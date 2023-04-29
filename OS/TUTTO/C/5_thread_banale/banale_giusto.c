/* banale_giusto.c  */

/*  i simboli _THREAD_SAFE _REENTRANT _POSIX_C_SOURCE=200112L
*   sono stati definiti nella riga di comando per la compilazione con il gcc
*   nel Makefile
*/

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 5

int Glob=10;

void *PrintHello(void *p_index)
{
	Glob+=3;
	sleep(4);
	printf("\n index %d: Hello World! Glob=%d\n", *((int*)p_index), Glob);
	free(p_index);
	pthread_exit (NULL);
}

int main()
{
	pthread_t tid;
	int rc, t, *p;                    /* notare che ho aggiunto una variabile puntatore p */
	for(t=0;t < NUM_THREADS;t++){

		/* far vedere cosa accade se alloco spazio solo una volta */

		p=malloc(sizeof(int));       /* alloco memoria sullo heap in cui mettere i parametri per i pthread */
		if(p==NULL) {
			perror("malloc failed: ");
			pthread_exit (NULL);
		}
		*p=t;
		printf("Creating thread %d\n", t);
		rc = pthread_create (&tid, NULL, PrintHello, p );  /* NOTARE ora l'ultimo parametro */
		if (rc){
			printf("ERROR; return code from pthread_create() is %d\n",rc);
			exit(-1);
		}

	}

	printf("fine main\n"); fflush(stdout);
	
	pthread_exit (NULL); /* anche qui ho tolto il commento */
	return(0);
}
