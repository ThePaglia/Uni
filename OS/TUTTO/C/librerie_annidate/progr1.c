/* progr1.c */

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

#include "funzioni1.h"

int main (void)
{
	int i, ris;
	char line[100], *p;

	fprintf(stderr,"waiting for input before using dll functions ...");
	fflush(stderr);
	p=fgets(line,99,stdin);

	for( i=0; i<20; i++ )
	{
		ris=incrementa();
		printf("%d\n", ris);
		fflush(stdout);
		sleep(1);
		if(i==10) {
			fprintf(stderr,"waiting for input while using dll functions ...");
			fflush(stderr);
			p=fgets(line,99,stdin);
		}
	}

	fprintf(stderr,"waiting for input after using dll functions ...");
	fflush(stderr);
	p=fgets(line,99,stdin);

	p=p;	/* per eliminare warning */

	return(0);
}

