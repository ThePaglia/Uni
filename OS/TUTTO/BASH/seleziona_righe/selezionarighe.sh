#!/bin/bash

#  righe.sh

if (( "$#" != "2" )) ; then
	echo "numero argomenti errato"  1>&2
	exit 1
fi

if [[ ! -e "$1" ]] ; then
	echo "argomento non file"  1>&2
	exit 2
fi

( sleep 2 ;  grep $2 $1 | wc -l >> ./OUTPUT.txt ;  ) &

exit 0

