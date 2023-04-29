#!/bin/bash

while read RIGA ; do
	TERZOeQUARTO=${RIGA#\"*\"*\"*\"}
#	echo TERZOeQUARTO is ${TERZOeQUARTO}
	TERZO=${TERZOeQUARTO%\"*\"*\"}\"
	echo TERZO is ${TERZO}
	
done < cadutevic.txt


