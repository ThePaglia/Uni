#!/bin/bash

read RESTO1 RESTO2 NUM < define.h

echo ${NUM}

for ((i=1;i<=NUM;i++)); do
	echo "extern int var$i;"
done >> variabiliglobali.h

echo "extern int conta(void);" >> variabiliglobali.h

