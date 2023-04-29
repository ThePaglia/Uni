#!/bin/bash

while read nome2 cognome2 numMat2 voto2 altro2; do
	if [[ $voto2 -lt 18 ]]; then
		while read nome1 cognome1 numMat1 voto1 altro1; do
			si=0
			if [[ $numMat2 -eq $numMat1 ]]; then
				si=1
				break
			fi
		done < RisultatiProvaPratica1.txt

		if [[ $si -eq 0 ]]; then 
			echo $numMat2 $nome2 $cognome2 $voto2
		fi
	fi
done < RisultatiProvaPratica2.txt
