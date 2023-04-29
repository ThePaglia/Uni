#include <stdio.h>
#include "dimezza.h"
#include "quadrato.h"

int main(void) {
	double dimezzato = dimezza(13.17);
	double squadrato = quadrato(dimezzato);

	printf("dimezzato=%f\n", dimezzato);
	printf("quadrato=%f\n", squadrato);
	return 0;
}
