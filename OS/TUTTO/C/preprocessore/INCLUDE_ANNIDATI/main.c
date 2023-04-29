#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

#include "avvicina.h"
#include "distanza.h"

void main(void){ 
  Punto A={1,3}, B={2,2}; 
  avvicinaAllOrigine(&B);
  printf("%f \n", distanza(A,B) );
}


