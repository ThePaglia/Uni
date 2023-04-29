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
POS=0
LEN=${#PASSOLUTO}
for(( POS=0; ${POS}<${LEN}; POS=${POS}+1 )) ; do
	if [[ "${PASSOLUTO:${POS}:1}" == "/" ]] ; then
		(( SUBLEN=${POS}+1 ))
		PERCORSOPARZIALE=${PASSOLUTO:0:${SUBLEN}}
		echo trovato ${PERCORSOPARZIALE}

		for name in ${PERCORSOPARZIALE}*l*i* ; do 
			#echo candidato ${name}
			#verifico che sia una directory
			if [[ -d ${name} ]] ; then
				solonome=${name#${PERCORSOPARZIALE}}
				echo ${solonome} ${#solonome}
			fi
		done
	fi
done

