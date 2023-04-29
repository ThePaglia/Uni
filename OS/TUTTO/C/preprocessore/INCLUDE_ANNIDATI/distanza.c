#include <unistd.h>
#include <stdlib.h>
#include <math.h>       /* per sqrt */

#include "struttura.h"

double distanza(Punto A, Punto B) {
     return( sqrt( (A.x-B.x)*(A.x-B.x) + (A.y-B.y)*(A.y-B.y) ) ); } 


