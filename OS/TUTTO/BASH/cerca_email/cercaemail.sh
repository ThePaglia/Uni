#!/bin/bash

if (( "$#" != "3" )) ; then
	echo "cercaemail.sh: numero argomenti errato"
	exit 1
fi

NOME=$1
COGNOME=$2
MATRICOLA=$3

# NB: le matricole sono univoche, quindi ci sara' una sola riga

RIGA=`cat ./email.txt | grep ${MATRICOLA} `

if [[ -z ${RIGA} ]] ; then
	echo "cercaemail.sh: account non trovata"
	exit 1
fi

# SE NEL FILE email.txt avete messo degli spazi bianchi
# invece dei TAB allora bisogna specificare al comando cut che 
# come delimitatore bisogna usare uno spazio bianco  --delimiter=" "

EMAIL=`echo  ${RIGA} | cut --delimiter=" " -f 2 `

echo "la email dello studente ${NOME} ${COGNOME} ${MATRICOLA} e' ${EMAIL} "

exit 0

