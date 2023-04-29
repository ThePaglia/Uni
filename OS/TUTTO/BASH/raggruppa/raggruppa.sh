#!/bin/bash

FILEDIAPPOGGIO=temp.txt

if [[ -e ${FILEDIAPPOGGIO} ]] ; then
	rm -f ${FILEDIAPPOGGIO}
fi

while read DATA LUOGO MOTIVO DANNI ; do
	NUM=`grep ${MOTIVO} cadutevic.txt | wc -l`
	echo "${MOTIVO} ${NUM}" >> ${FILEDIAPPOGGIO}
done < cadutevic.txt

sort ${FILEDIAPPOGGIO} | uniq

rm -f ${FILEDIAPPOGGIO}

