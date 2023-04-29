#!/bin/bash

if (( "$#" != "2" )) ; then
	echo "cercastudente.sh: numero argomenti errato"
	exit 1
fi

EMAIL=$1
MATRICOLA=$2

RIGA=`cat ./matricola.txt | grep ${MATRICOLA} | head -n 1 `

if [[ -z ${RIGA} ]] ; then
	echo "cercastudenti.sh: account non trovata"
	exit 1
fi

# ora RIGA ha un formato di    nome cognome matricola cdl

# SE NEL FILE matricola.txt avete messo degli spazi bianchi
# invece dei TAB allora bisogna specificare al comando cut che 
# come delimitatore bisogna usare uno spazio bianco  --delimiter=" "

# MODO CON DELIMITATORE SPAZIO
# NOME=`echo  ${RIGA} | cut --delimiter=" " -f 1 `
# COGNOME=`echo  ${RIGA} | cut --delimiter=" " -f 2 `

# MODO CON DELIMITATORE TAB

# isolo il primo campo, cioe' elimino tutto quello che sta dopo il primo TAB
NOME=${RIGA%%	*}

# echo "NOME ${NOME} "

# butto quello che c'e' dopo il secondo campo, cioe' elimino tutto quello che sta dopo il secondo TAB
COGNOME=${RIGA%	*	*}
# isolo il secondo campo, cioe' elimino tutto quello che sta prima del primo TAB
COGNOME=${COGNOME#*	}

# echo "COGNOME ${COGNOME}"


echo "la account ${EMAIL} appartiene allo studente ${NOME} ${COGNOME} "

exit 0

