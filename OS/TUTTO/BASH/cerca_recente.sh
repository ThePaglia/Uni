#!/bin/bash

FILES=$(find '/usr/include/linux' -name "*.h" -type f)

for RECENTE in $FILES; do
	break
done

for NAME in $FILES; do
	if [[ $RECENTE -ot $NAME ]]; then
		RECENTE=$NAME
	fi
done

echo $RECENTE




