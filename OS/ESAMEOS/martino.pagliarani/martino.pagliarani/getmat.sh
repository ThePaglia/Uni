#!/bin/bash

PREV=""
while read PRIMO_ELEM SECONDO_ELEM; do
	if [[ ${SECONDO_ELEM} == "OPERATIVI" ]]; then
	echo "${PREV}"
 	fi
PREV="${PRIMO_ELEM}"
done < lista.txt

