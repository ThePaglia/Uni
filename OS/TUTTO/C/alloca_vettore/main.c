#include <unistd.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>
#include "macro.h"
int main(void) {
	int32_t *p;
	int i;
	ALLOCAVETTORE(p);
	for(i=0;i<10;i++)
		p[i]=-19+i;
	for(i=0;i<10;i++) {	
		printf("%d ", p[i] );
		fflush(stdout);
	}
	printf("\n");	
	return(0);
}

