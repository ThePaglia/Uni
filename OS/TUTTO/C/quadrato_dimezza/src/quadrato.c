#include <stdio.h>

static double salva;

double quadrato(double num) {
	salva = num*num;
	return(salva);
}
