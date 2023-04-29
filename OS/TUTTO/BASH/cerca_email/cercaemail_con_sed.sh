#!/bin/bash

# PER I MANIACI PIU' FUORI DI TESTA .....
# grazie Matteo Cristello !

if (( "$#" != "3" )) ; then
        echo "cercaemail: numero argomenti errato"
        exit 1
fi

NOME=$1
COGNOME=$2
MATRICOLA=$3

LINE=`grep "${MATRICOLA}" ./email.txt`

if [[ -z "${LINE}" ]] ; then
        echo "cercaemail: account non trovata"
        exit 1
fi

EMAIL=`echo ${LINE} | sed -r 's/.{4}//'`


echo "la email dello studente $NOME $COGNOME $MATRICOLA e' ${EMAIL}"

