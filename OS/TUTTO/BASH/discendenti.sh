#!/bin/bash

if [[ $1 -gt 0 ]]; then
	for(( var=$1-1; $var>0; var=$var-1 )); do
		./discendenti.sh $var
	done
fi
echo $1
