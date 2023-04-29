#!/bin/bash

if (( $# != 1 )) ; then echo "serve il nome di un file in cui scrivere"; exit 1; fi

echo $( ./random09.sh ) >>  $1

