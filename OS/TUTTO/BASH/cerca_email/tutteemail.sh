#!/bin/bash

while read NOME COGNOME MATRICOLA CorsoDiLaurea ALTRO ; do 

	if [[ -z ${NOME} || -z ${COGNOME} || -z ${MATRICOLA} || -z ${CorsoDiLaurea} || -n ${ALTRO} ]] ; then
		echo "read found no student, stop"
		exit 1
	fi

	./cercaemail.sh ${NOME} ${COGNOME} ${MATRICOLA}

done < matricola.txt

exit 0

