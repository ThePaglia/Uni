#!/bin/bash

FILES=`find /usr/include/linux/ -maxdepth 1 -name "*f*.h" -print`
if [[ -z ${FILES} ]] ; then
	echo "nessun file trovato"
else
	for NAME in ${FILES} ; do
		NUM=`wc -l ${NAME}`
		NUM=${NUM%% *}
		if [[ ${NUM} -ge 10 &&  ${NUM} -le 100 ]] ; then
			NUM=`tail -n 1 ${NAME} | wc -m`
			echo ${NUM}
		fi
	done
fi

