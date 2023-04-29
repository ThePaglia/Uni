#include <unistd.h>
#include <stdlib.h>

#include "strutture.h"

/* calcola la distanza seguendo percorsi solo orrizontalie verticali */
double distanza(Punto A, Punto B) {
	double d;
	if( A.x>B.x )	d=A.x-B.x;
	else		d=B.x-A.x;
	if( A.y>B.y )	d=d+(A.y-B.y);
	else		d=d+(B.y-A.y);
	return(d);
}

