#!/bin/bash

while read data luogo motivo danni; do
	NUMEROTIPO=`grep $motivo cadutevic.txt | wc -l`
	echo "$motivo $NUMEROTIPO" >> out.txt
done < cadutevic.txt

sort out.txt | uniq

rm out.txt
