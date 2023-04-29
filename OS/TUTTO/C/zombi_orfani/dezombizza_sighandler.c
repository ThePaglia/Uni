/* dezombizza_sighandler.c
   compilare con -D_POSIX_C_SOURCE >= 200112L
*/

/* messo prima perche' contiene define _POSIX_C_SOURCE */
#include "printerror.h"


#include <stdlib.h>
#include <unistd.h>   /* exit() etc */
#include <signal.h>   /* sigaction, sigemptyset, struct sigaction, SIGCHLD, SA_RESTART, SA_NOCLDSTOP */
#include <stdio.h>    /* perror  */
#include <stdlib.h>   /* exit  */
#include <sys/wait.h> /* waitpid, pid_t, WNOHANG */
#include <errno.h>
#include <string.h>     /* per strerror_r  and  memset */


void register_SIGCHLD_sighandler(void);

/* il padre gestisce in modo asincrono
   la terminazione del figlio 
*/
void dezombizzaFigli(int signum) {
    pid_t pid;
    int saved_errno = errno;
    /*
      si usa while perche'
        multiple child processes could terminate while 
        the handler processes one SIGCHLD signal.
        Only one instance of SIGCHLD can be queued,
        so it may be necessary to reap several zombie processes
        during one invocation of the handler function.
    */
    while ( ( pid= waitpid((pid_t)(-1), 0, WNOHANG) ) > 0) {
        printf("signal %d from pid %i \n", signum, pid );
        fflush(stdout);
    }
    errno = saved_errno;
}

void register_SIGCHLD_sighandler(void) {
    /* REGISTER signal handler */
    struct sigaction sa;
    sa.sa_handler = &dezombizzaFigli;
    sigemptyset(&sa.sa_mask);
    /*
      use SA_NOCLDSTOP because
        the SIGCHLD signal indicate that a child process
        has terminated, or stopped or continued.
        We are interested in terminated, but ....
    */
    /* sa.sa_flags = SA_RESTART | SA_NOCLDSTOP; */

    sa.sa_flags = SA_NOCLDSTOP;
    if (sigaction(SIGCHLD, &sa, 0) == -1) {
        PrintERROR_andExit(errno,"sigaction() failed ");
    }
}

int main() {
    pid_t pid;
    int i;

    /* REGISTER signal handler */
    register_SIGCHLD_sighandler();

    for( i=0; ; i++ ) {
        pid=fork();
        if(pid<0) { 
            PrintERROR_andExit(errno,"fork() failed ");
        }
        else if ( pid == 0) { /* figlio */
            printf("figlio inizia %i \n", getpid() );
            fflush(stdout);
            sleep(2);
            printf("figlio termina %i \n", getpid() );
            fflush(stdout);
            exit(0);
        }
        else {  /* padre */
	    int residuo;
	    residuo=sleep(10);
	    if( residuo>0 ) {
            	printf("attesa del padre interrotta da signal\n" );
            	fflush(stdout);
	    }
        }
    }
    return(0);
}

