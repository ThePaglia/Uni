#!/bin/bash

echo "ho passato $# argomenti alla shell" 
echo "il nome del processo in esecuzione e' $0" 
echo "gli argomenti passati a riga di comando sono $*" 

for name in  $*  ;  do 
	echo "argomento e’  ${name}" ; 
done

