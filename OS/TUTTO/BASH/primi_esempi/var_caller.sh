#!/bin/bash

#	var_caller.sh

echo "caller" 
# setto la var locale PIPPO
# la var d’ambiente PATH esiste già

PIPPO=ALFA
echo "PATH = ${PATH}"
echo "PIPPO = ${PIPPO}"

echo "calling subshell" 

./var_called.sh

echo "ancora dentro caller" 
echo "variabili sono state modificate ?"

echo "PATH = ${PATH}"
echo "PIPPO = ${PIPPO}"

