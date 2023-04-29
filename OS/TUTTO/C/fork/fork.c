/* fork.c  
   a causa dell'uso di strerror_r, usato in printerror.h,
   compilare con -D_POSIX_C_SOURCE >= 200112L
*/


#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

#include <signal.h>
#include <sys/time.h>	/* timeval{} for select() */
#include <time.h>	/* timespec{} for pselect() */
#include <string.h>     /* per strerror_r  and  memset */
#include <limits.h>	/* for OPEN_MAX */
#include <errno.h>
#include <sys/wait.h>

#include "printerror.h"

int esegui( void ) 
{
	pid_t pid;
	int status;

	pid=fork();	/* il padre fa la prima fork */
	if(pid<0)
	{
		int myerrno=errno;
		fprintf(stderr, "fork() failed: error %d\n", myerrno );
		fflush(stderr);
		exit(1);
	}
	else if(pid==0) /* il figlio */
	{
		int k;
		for( k=0; k<10; k++) {
			sleep(1);
			printf("figlio\n"); fflush(stdout);
		}
		printf("sono il figlio e termino\n"); fflush(stdout);
		exit(9);
	}
	else 
	{		/* il padre */
		int k; 
		pid_t childpid;

		/* decommentare per vedere cosa succede  se il padre chiama una wait
		   molto tempo dopo che i figli sono gia' morti. 
		   Cosi' si fa in tempo a vedere con un ps che i figli morti sono zombie.
		*/
		/* sleep(60); */

		for( k=0; k<5; k++) {
			sleep(1);
			printf("padre\n"); fflush(stdout);
		}
		/* il padre aspetta la terminazione del figlio */
  		do {
			childpid = waitpid ( pid, &status, 0 /*WNOHANG*/ );
		} while( (childpid<0) && (errno==EINTR) );
		if( childpid<0 )	
		{
			PrintErrnoAndExit ( "waitpid" );
			exit(3);
		}
		else if( childpid==0 ){ /* solo se ho usato WNOHANG */
			printf("il processo figlio non e' ancora terminato");
		}
		else {
			if(  WIFEXITED(status) ) { /* figlio terminato normalmente */
				int rc;
				rc =   WEXITSTATUS(status);
				printf("padre: figlio restituisce status %d\n", rc );
				fflush(stdout);
			}
			else {
				printf("figlio termina in modo anormale\n" );
				fflush(stdout);
			}
		}

	}
	return(0);

}


int main( int argc, char* argv[] ) {
	esegui(  );
	return(0);
}

