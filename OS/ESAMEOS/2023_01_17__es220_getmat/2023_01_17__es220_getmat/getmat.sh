#!/bin/bash

cat lista.txt |  grep -B 1 --no-group-separator OPERATIVI | grep -v OPERATIVI  | while read MATRICOLA COGNOME NOME ; do echo ${MATRICOLA} ; done


