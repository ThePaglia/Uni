#!/bin/bash

if (( $# != 2 )) ; then echo "servono due nomi di file"; exit 1; fi

if [[ -e out.txt ]] ; then rm -f out.txt; fi
exec   {FD1}<  $1
exec   {FD2}<  $2
WorkingFD=${FD1}
while read  -u   ${WorkingFD}  StringaLetta ; RIS=$?; [[ ${StringaLetta} != "" || ${RIS} -eq 0  ]] ; do
	echo "${StringaLetta}" >> out.txt
	if (( ${RIS} != 0 )) ; then 
		break
	else
		if (( WorkingFD==${FD1} )) ; then	WorkingFD=${FD2} ; 
		else				WorkingFD=${FD1} ; 
		fi
	fi
done
if (( ${WorkingFD}==${FD1} )) ; then	
	exec {FD1}>&-
	WorkingFD=${FD2} ; 
else
	exec {FD2}>&-
	WorkingFD=${FD1} ; 
fi

while read  -u   ${WorkingFD}  StringaLetta ; RIS=$?; [[ ${StringaLetta} != "" || ${RIS} -eq 0  ]] ; do
	echo "${StringaLetta}" >> out.txt
	if (( ${RIS} != 0 )) ; then 
		break
	fi
done
exec {WorkingFD}>&-
echo fine
