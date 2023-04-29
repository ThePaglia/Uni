/* es00000_facile.c  */

#ifndef _THREAD_SAFE
#define _THREAD_SAFE
#endif

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 10


void *fai(void *p)
{
	sleep(5);
	printf("%f\n", *((double*)p));
	free(p);
	pthread_exit (NULL);
}

int main()
{
	pthread_t th;
	int rc, t;
	double *p;
	srand( time(NULL) );
	for(t=0; t<NUM_THREADS; t++){
		p=malloc(sizeof(double));
		if(p==NULL) {
			perror("malloc failed: ");
			pthread_exit (NULL);
		}
		*p=(double) (rand()%100000);
		printf("Creating thread %d\n", t);
		rc = pthread_create (&th, NULL, fai, p );
		if (rc){
			printf("ERROR; return code from pthread_create() is %d\n",rc);
			exit(-1);
		}
	}
	printf("fine main\n"); fflush(stdout);
	
	pthread_exit (NULL);
	return(0);
}

