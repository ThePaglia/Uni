#!/bin/bash

conta=0
while read riga; do
	if [[ $riga == *a* ]]; then
		(( conta=${conta}+${#riga} ))
	fi
done < $1

echo ${conta}

#grep A | wc -c
