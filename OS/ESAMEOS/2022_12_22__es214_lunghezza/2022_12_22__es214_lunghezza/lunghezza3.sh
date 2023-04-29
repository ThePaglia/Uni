#!/bin/bash

if (( $# != 1 )) ; then 
	echo "serve un argomento che"
	echo "sia un percorso assoluto di una directory esistente"
	echo "e che termini con uno slash /"
	echo "ad es: /usr/include/linux/ "
  echo "per prova metto un percorso assoluto di prova"
  PASSOLUTO="/usr/include/linux/";
else
  PASSOLUTO=$1;
fi

COUNTER=1
PERCORSOPARZIALE=""

while true ; do
	STEP=`echo ${PASSOLUTO} | sed 's/\//\/ /g'  | cut --delimiter=" " -f ${COUNTER}`
	#echo ${COUNTER} STEP=${STEP}
	if [[ -z ${STEP} ]] ; then
		break
	else
		(( COUNTER=${COUNTER}+1 ))
		PERCORSOPARZIALE="${PERCORSOPARZIALE}${STEP}"
		# echo trovato ${PERCORSOPARZIALE}

		for name in ${PERCORSOPARZIALE}*l*i* ; do 
			#echo candidato ${name}
			if [[ -d ${name} ]] ; then
				#rimuovo il piu' corto prefisso uguale al PERCORSOPARZIALE
				solonome=${name#${PERCORSOPARZIALE}}
				echo ${solonome} ${#solonome}
			fi
		done
	fi

done

