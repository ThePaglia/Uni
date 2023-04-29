#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define SIMBOLO  100
#undef SIMBOLO


int main(void)
{

#ifndef SIMBOLO
	printf("SIMBOLO NON DEFINITO  \n");
#else
	printf("SIMBOLO DEFINITO  \n");
#endif	

	return(0);
}

