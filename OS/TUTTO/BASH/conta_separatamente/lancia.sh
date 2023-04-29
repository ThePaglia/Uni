#!/bin/bash

./contaseparatamente.sh `ls -S1 /usr/include/*.h | head -n 7 ` 1>&2

if (( $? != 0 )) ; then
	echo errore in ./contaseparatamente.sh
	exit 1
fi

exit 0

