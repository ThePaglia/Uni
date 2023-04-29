/* notifica_semplice.c */

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

pthread_mutex_t	mutex;
pthread_cond_t	cond_vuoto;
pthread_cond_t	cond_pieno;

int							num=0;
pthread_t				threadId;



void *avvisa(void *arg){
		char Alabel[128]; 
		int rc;
		pthread_t th;

		th = pthread_self();
		sleep(1);

		DBGpthread_mutex_lock(&mutex, Alabel);
		while(num!=0){
			DBGpthread_cond_wait(&cond_vuoto,&mutex, Alabel);
		}

		num++;
		threadId=th;
		
		printf("avvisa passa th %lu\n",th);
		fflush(stdout);
		DBGpthread_cond_signal(&cond_pieno, Alabel);
		
		DBGpthread_mutex_unlock(&mutex, Alabel);

		printf("avvisa th %lu termina \n",th);
		fflush(stdout);


		rc=pthread_create( &th, NULL, avvisa, (void*)NULL ); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");

		pthread_exit(NULL); 
}


int main(){
	int rc,i;
	pthread_t	th;
	
	rc = pthread_cond_init(&cond_vuoto, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_cond_init(&cond_pieno, NULL);
	if( rc ) PrintERROR_andExit(rc,"pthread_cond_init failed");
	rc = pthread_mutex_init(&mutex, NULL); 
	if( rc ) PrintERROR_andExit(rc,"pthread_mutex_init failed");

	num=0;	
	
	/*creo i thread */
	for(i=0;i<10;i++) {
		rc=pthread_create( &th, NULL, avvisa, (void*)NULL ); 
		if(rc) PrintERROR_andExit(rc,"pthread_create failed");
	}

	for(;;) {
		void *p=NULL;
		DBGpthread_mutex_lock(&mutex, "main");
		while(num==0){
			DBGpthread_cond_wait(&cond_pieno,&mutex, "main");
		}
		printf("main riceve th %lu\n",th);
		fflush(stdout);

		th = threadId;
		num--;
		DBGpthread_cond_signal(&cond_vuoto, "main");
		DBGpthread_mutex_unlock(&mutex, "main");
		
		rc=pthread_join( th, &p  ); 
		if(rc) PrintERROR_andExit(rc,"pthread_join failed");

		printf("main riconosce terminazione th %lu\n",th);
		fflush(stdout);
	}


	pthread_exit(NULL); 
	return 0;
}
