#!/bin/bash

read RESTO1 RESTO2 VALORE < ./define.h

echo ${VALORE}

rm -f variabiliglobali.c
for (( NUM=1; ${NUM}<=${VALORE}; NUM=${NUM}+1 )) ; do
	echo "int var${NUM} = ${NUM} ;" >> variabiliglobali.c
done

echo 'int conta(void) {' >> variabiliglobali.c
echo -n 'int sum=0' >> variabiliglobali.c
for (( NUM=1; ${NUM}<=${VALORE}; NUM=${NUM}+1 )) ; do
	echo -n "+var${NUM}" >> variabiliglobali.c
done
echo ';' >> variabiliglobali.c
echo 'return(sum);' >> variabiliglobali.c
echo '}' >> variabiliglobali.c
