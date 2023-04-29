#!/bin/bash

if (( $# != 1 )) ; then 
	echo "serve un argomento che"
	echo "sia un percorso assoluto di una directory esistente"
	echo "e che termini con uno slash /"
	echo "ad es: /usr/include/linux/ "
	echo "per prova metto un percorso assoluto di prova"
  PERCASSOLUTO="/usr/include/linux/";
else
	PERCASSOLUTO=$1;
fi

PEZZITERMINATIDASLASH=`echo ${PERCASSOLUTO} | sed 's/\//\/ /g' `
echo ${PEZZITERMINATIDASLASH}

PERCORSOPARZIALE=""
for PEZZO in ${PEZZITERMINATIDASLASH} ; do
	PERCORSOPARZIALE=${PERCORSOPARZIALE}${PEZZO}

	#echo trovato ${PERCORSOPARZIALE}

	for name in ${PERCORSOPARZIALE}*l*i* ; do 
		#echo candidato ${name}
		#verifico che sia una directory
		if [[ -d ${name} ]] ; then
			solonome=${name##*/}
			echo ${solonome} ${#solonome}
		fi
	done
done

