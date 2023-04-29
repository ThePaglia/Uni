#!/bin/bash
exec {FD}< ./miofileNoNL.txt
if (( $? == 0 )) ; then
	while read -u ${FD} RIGA ; [[ $? == 0 || ${RIGA} != "" ]] ; do
		echo RIGA "${RIGA}"
	done
	exec {FD}>&-
fi

