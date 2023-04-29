#!/bin/bash

if (( $# != 1 )) ; then 
	echo "serve un argomento che"
	echo "sia un percorso assoluto di una directory esistente"
	echo "e che termini con uno slash /"
  echo "per prova metto un percorso assoluto di prova"
  PASSOLUTO="/usr/include/linux/";
else
  PASSOLUTO=$1;
fi
P1=${PASSOLUTO}
while [[ -n ${P1} ]] ; do
	#echo P1 ${P1}
	PERCORSOPARZIALE=${P1}
	#echo trovato ${PERCORSOPARZIALE}

	for name in ${PERCORSOPARZIALE}*l*i* ; do 
		#echo candidato ${name}
		if [[ -d ${name} ]] ; then
			#rimuovo il piu' corto prefisso uguale al PERCORSOPARZIALE
			solonome=${name#${PERCORSOPARZIALE}}
			echo ${solonome} ${#solonome}
		fi
	done

	#echo P1 ${P1}
	#se P1 ha piu' del solo slash finale, allora
	if [[ ${P1} != "/" ]] ; then 
		#rimuovo il piu' corto suffisso che inizia con / e finisce con slash
		#P1 rimane senza lo slash in fondo
		P1=${P1%/*/}
		#e gli aggiungo uno slash in fondo per avere il percorso parziale
		P1=${P1}/
	else
		break
	fi
	#echo ora P1 == ${P1}

done

