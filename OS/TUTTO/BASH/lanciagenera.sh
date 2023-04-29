#!/bin/bash

if [[ -e out.txt ]] ; then rm -f out.txt; fi

for (( NUM=0; ${NUM}<10; NUM=${NUM}+1 )) ; do
	./genera.sh out.txt
done
SUM=0
while read  VALORE ALTRO ; do
	((SUM=${SUM}+VALORE))
done < out.txt

echo ${SUM}

