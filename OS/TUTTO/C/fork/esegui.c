/* esegui.c  
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
	int ret, status;

	pid=fork();	/* il padre fa la prima fork */
	if(pid<0)
	{
		int myerrno=errno;
		fprintf(stderr, "fork() failed: error %d\n", myerrno );
		fflush(stderr);
		exit(1);
	}
	else if(pid==0) /* il figlio esegue il comando script.sh */
	{
		int myerrno;
									 
		/* eseguo un altro programma chiamando execl  
		The  exec()  family  of functions replaces 
		the current process image with a new process image.
		*/
		do {
			/* servono almeno due parametri: path+nome del programma 
			   e nome del programma da eseguire
			*/
			/*
			ret=execl( "/bin/bash", "bash", "-c", "ps aux | grep esegui", 
				   (char*)NULL );
			*/

			ret=execl( "./script.sh", "script.sh", (char*)NULL );
			
			/*
			ret=execl( "/usr/bin/find", "find", "/",
				   "-maxdepth", "1", "-name", "*u*", "-print",
				   (char*)NULL );
			*/

			myerrno=errno; /* normally, not reached */

		} while( (ret<0) && (myerrno==EINTR) );
		if(ret<0) {
			PrintErrnoAndExit( "execl" );
			exit(2);
		}
	}
	else 
	{
		pid_t pidGrazieAndrea;

		/* il padre aspetta la terminazione del figlio */
  		do {
			pidGrazieAndrea = waitpid ( pid, &status, 0 /*WNOHANG*/ );
		} while( (pidGrazieAndrea<0) && (errno==EINTR) );
		if(pidGrazieAndrea<0)	
		{
			PrintErrnoAndExit ( "waitpid" );
			exit(3);
		}
                else if( pidGrazieAndrea==0 ){ /* puo' accadere solo se ho usato WNOHANG */
                        printf("il processo figlio non e' ancora terminato");
                }
		else {
			if(  WIFEXITED(status) ) { /* figlio terminato normalmente */
				int rc;
				rc =   WEXITSTATUS(status);
				printf("figlio restituisce status %d\n", rc );
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


/* servono due parametri: path+nome del programma 
   e nome del programma da eseguire
*/
int main( int argc, char* argv[] ) {
	esegui(  );
	return(0);
}

