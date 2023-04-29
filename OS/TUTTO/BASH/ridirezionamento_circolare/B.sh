#!/bin/bash

# B.sh

rm -f B.txt

while read NUM ALTRO ; do
	echo ${NUM}
	echo ${NUM} >> B.txt
done

exit 0

