
/* Nota Bene: per semplicita' sono state tolte
   le righe corrispondenti agli include
*/


int main(void) {

	printf( "inizio\n" );

	srandom( time(((void *)0)) ); if( random()%300 < 150 ) printf( "ciao\n" ); else printf( "la vacca ti ha fatto \n" );

	printf( "fine\n" );
	return(0);
}
