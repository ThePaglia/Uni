#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#undef NUM  
#define NUM 60  

int main(void)
{

#ifdef NUM
	#if NUM < 100 && ( NUM > 50 || NUM < 5 ) 
		printf("RECEPITO NUM < 100 and ....  \n");
	#endif	

	#if NUM == 100
		printf("RECEPITO NUM == 100 \n");
	#else
		#if NUM == 101
			printf("RECEPITO NUM == 101 \n");
		#else
			printf("RECEPITO NUM != 100 e 101 \n");
		#endif
	#endif
	 
#endif
	return(1);
}
