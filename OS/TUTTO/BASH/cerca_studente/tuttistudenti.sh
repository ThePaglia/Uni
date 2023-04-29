#!/bin/bash

while read MATRICOLA EMAIL ALTRO ; do 

	if [[ -z ${EMAIL} || -z ${MATRICOLA} || -n ${ALTRO} ]] ; then
		echo "read found no student, stop"
		exit 1
	fi

	./cercastudente.sh ${EMAIL} ${MATRICOLA}

done  < email.txt

exit 0

