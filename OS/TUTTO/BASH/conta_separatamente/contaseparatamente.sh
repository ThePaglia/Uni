#!/bin/bash

if (( "$#" == "0" )) ; then
	echo "pochi argomenti"
	exit 1
fi
if (( "$#" > "9" )) ; then
	echo "troppi argomenti"
	exit 1
fi

NUM=1
COUNTERPARI=0
COUNTERDISPARI=0
while (( "${NUM}" <= "$#" ))
do
	RIGHE=`wc -l ${!NUM}`
	RIGHE=${RIGHE%% *}

	if (( ${NUM}%2 == 0 )); then
		(( COUNTERPARI=${COUNTERPARI}+${RIGHE}  ))
	else
		(( COUNTERDISPARI=${COUNTERDISPARI}+${RIGHE}  ))
	fi

	((NUM=${NUM}+1))
done

echo ${COUNTERPARI}
echo ${COUNTERDISPARI} 1>&2 

exit 0

