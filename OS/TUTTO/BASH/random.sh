#!/bin/bash

CONTALETTERA=0

while [[ $(( $RANDOM % 10 )) != 2 ]]; do
	(( CONTALETTERA=${CONTALETTERA}+1 ))
done

echo ${CONTALETTERA}
