/* es3bis_Deadlock_fachiri.c  */

/* messi anche nella riga di comando del compilatore */
#ifndef _THREAD_SAFE
#define _THREAD_SAFE
#endif
#ifndef _REENTRANT
#define _REENTRANT
#endif

#ifdef  _POSIX_C_SOURCE
#if     _POSIX_C_SOURCE < 200112L
#undef  _POSIX_C_SOURCE
#define _POSIX_C_SOURCE 200112L
#endif
#else
#define _POSIX_C_SOURCE 200112L
#endif


#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
/* per intptr_t */
#include <stdint.h>
/* PRIiPTR */
#include <inttypes.h>
/* per strerror_r */
#include <string.h>
#include <pthread.h>

#define NUMFACHIRI 2
#define NUMSPADE 10

pthread_mutex_t mutex[NUMSPADE];

void stampaerrore(int errorcode, char *str)
{
	char msg[100];
	strerror_r(errorcode,msg,100);
	printf("%s error %u %s\n", str, errorcode, msg);
	fflush(stdout);
}

void *fachiro(void *arg)
{
	int rc, i;
	while(1) {
		if( (intptr_t)arg == 0 ) { 
			for(i=0;i<NUMSPADE;i++) {
				printf("fachiro  %" PRIiPTR " cerca di prendere spada %i\n", (intptr_t)arg ,i);
				fflush(stdout);
				fflush(stderr);
				rc=pthread_mutex_lock(&(mutex[i]));
				if(rc!=0) {stampaerrore(rc,"lock failed\n");exit(1);}
				fflush(stdout);
				fflush(stderr);
			}
		} else {
			for(i=NUMSPADE-1;i>=0;i--) {
				printf("fachiro  %" PRIiPTR " cerca di prendere spada %i\n", (intptr_t)arg, i);
				fflush(stdout);
				fflush(stderr);
				rc=pthread_mutex_lock(&(mutex[i]));
				if(rc!=0) {stampaerrore(rc,"lock failed\n");exit(1);}
				printf("fachiro  %" PRIiPTR " ha preso spada %i\n", (intptr_t)arg, i);
				fflush(stdout);
				fflush(stderr);
			}
		}
		printf("fachiro  %"  PRIiPTR " si trafigge  ....  gooooodoooo\n", 
			(intptr_t)arg );
		fflush(stdout);
		if( (intptr_t)arg == 0 ) { 
			for(i=0;i<NUMSPADE;i++) {
				rc=pthread_mutex_unlock(&(mutex[i]));
				if(rc!=0) {stampaerrore(rc,"unlock failed\n");exit(1);}
				printf("fachiro  %"  PRIiPTR " rilascia spada %i\n", 
					(intptr_t)arg, i );
				fflush(stdout);
				fflush(stderr);
			}
		} else {
			for(i=NUMSPADE-1;i>=0;i--) {
				rc=pthread_mutex_unlock(&(mutex[i]));
				if(rc!=0) {stampaerrore(rc,"unlock failed\n");exit(1);}
				printf("fachiro  %"  PRIiPTR " rilascia spada %i\n", 
					(intptr_t)arg, i );
				fflush(stdout);
				fflush(stderr);
			}
		}
	}
	pthread_exit (NULL);
}

int main()
{
	pthread_t tid;
	int rc, i;
	intptr_t t;

	for(i=0;i<NUMSPADE;i++) {
		rc = pthread_mutex_init (&(mutex[i]), NULL);
		if (rc){stampaerrore(rc,"pthread_mutex_init failed");exit(1);}
	}
	for(t=0;t<NUMFACHIRI;t++){
		printf("Creating thread %" PRIiPTR "\n", t);
		rc = pthread_create (&tid, NULL, fachiro, (void*)t );
		if (rc){char msg[100];
			strerror_r(rc,msg,100);
			printf("pthread_create failed : %s\n", msg );
			exit(1);
		}
	}

	pthread_exit (NULL);
	return(0);
}
