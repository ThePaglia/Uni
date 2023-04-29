#!/bin/bash
sleep 2
TUTTO=
while read RIGA ; do
	# faccio parsing estraendo i pezzi e manipolo le stringhe con i quoting
	# per evitare problemi con gli asterischi e wildcard in genere.
	# Prima rimuovo un eventuale spazio banco iniziale
	RIGA="${RIGA# }"
	# itero fino a che la stringa ha qualcosa dentro
	while [[ -n "${RIGA}" ]] ; do
		# copio prima parola, quello che c'e' fino al primo bianco
		PAROLA="${RIGA%% *}"
		# se a fine riga non c'e' spazio bianco non sono riuscito ad estrarre la parola,
		# e ho preso tutta la riga
		# se ho preso tutta la riga vuol dire che sono alla fine.
		if [[ "${PAROLA}" == "${RIGA}" ]] ; then
			RIGA=
		else
			# rimuovo la prima parola e il successivo bianco
			RIGA="${RIGA#* }"
		fi
	if echo "${PAROLA}" | grep '*' > /dev/null ; then
		TUTTO="${TUTTO} ${PAROLA}"
	fi
	done
done
echo "${TUTTO}"



