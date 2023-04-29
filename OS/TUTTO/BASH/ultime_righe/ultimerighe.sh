#!/bin/bash

#  ultimerighe.sh

if (( "$#" != "1" )) ; then
	echo "numero argomenti errato"  1>&2
	exit 1
fi

if [[ ! -e "$1" ]] ; then
	echo "argomento non file"  1>&2
	exit 2
fi

( sleep 2 ;  tail -n 3 $1 >> ./OUTPUT.txt ;  ) &

exit 0

