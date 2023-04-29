#!/bin/bash

PRIMA=${PATH}

while [[ -n ${PRIMA} ]] ; do
	R=${PRIMA%%:*}
	TEST=${PRIMA}
	echo ${R}
	PRIMA=${PRIMA#*:}

	if [[ ${TEST} == ${PRIMA} ]] ; then
		break
	fi
done


#echo $PATH | sed 's/:/\n/g'
