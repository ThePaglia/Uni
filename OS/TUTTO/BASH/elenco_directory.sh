#!/bin/bash

LungNomiDirectory=0
NumFile=0
for name in `ls`; do
	if [[ -d "${name}" ]]; then
		 (( LungNomiDirectory=${LungNomiDirectory}+${#name} ))
	else 
		(( NumFile=${NumFile}+1 ))
	fi
done

echo ${LungNomiDirectory}
echo ${NumFile}
