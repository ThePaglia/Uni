#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


int main(void)
{

	/* 
	char str[11]="9876543210"; 
	*/

	char *str="9876543210";

	/* 
	char str[]="9876543210";
	*/

	printf("str= %s sizeof(str)= %ld \n", str, sizeof(str) );
	fflush(stdout);
	printf("PRIMA str= %s \n", str );
	fflush(stdout);

	str[1]='f'; 

	printf("DOPO str= %s \n", str );
	fflush(stdout);

	return(0);
}
