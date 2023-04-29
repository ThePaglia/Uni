#!/bin/bash

sino=1
while read RIGA; do
	if((${sino}==1)); then
		sino=0
		echo ${RIGA}
	else
		sino=1
	fi
done


