#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/* #undef NUM  */
/* #define NUM 60 */

/* NOTA BENE:  le direttive di compilazione condizionale
   POSSONO SOLO FARE CONFRONTI TRA INTERI, NON STRINGHE
#define STRING1 ALFABETA
#define STRING2 GAMMATETA
*/

int main(void)
{

#ifdef NUM
	#if NUM < 100 && ( NUM > 50 || NUM < 5 ) 
		printf("RECEPITO NUM < 100 and ....  \n");
	#endif	

	#if NUM == 100
		printf("RECEPITO NUM == 100 \n");
	/* SBAGLIATO #elsif NUM == 101 */
	#elif NUM == 101
		printf("RECEPITO NUM == 101 \n");
	#else
		printf("RECEPITO NUM != 100 e 101 \n");
	#endif

/* NON SI PUO FARE CONFRONTO TRA NON INTERI
	#if 	STRING1 != STRING2 
		printf("STRING1 != STRING2  \n");
	#else
		printf("STRING1 == STRING2  \n");
	#endif	
*/

	 
#endif
	return(1);
}
