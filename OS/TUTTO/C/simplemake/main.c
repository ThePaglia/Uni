#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

#include "funzioni.h"

int main(void){ 
	Punto A={1,3}, B={2,2}; 
	printf("%f \n", distanza(A,B) );
	return(0);
}


