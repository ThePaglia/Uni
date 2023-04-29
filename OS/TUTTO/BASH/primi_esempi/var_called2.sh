#!/bin/bash

#	var_called2.sh

echo "called" 
echo "le variabili sono state passate ? " 

echo "PATH = ${PATH}"
echo "PIPPO = ${PIPPO}"

echo "modifico variabili " 

PIPPO="${PIPPO}:MODIFICATO"
PATH="${PATH}:MODIFICATO"

echo "creo nuova  variabile" 
NUOVA=NUOVOCONTENUTO

echo "PATH = ${PATH}"
echo "PIPPO = ${PIPPO}"
echo "NUOVA = ${NUOVA}"

echo "termina called"

# che accade se DEcommento qui sotto?
# exit 13

