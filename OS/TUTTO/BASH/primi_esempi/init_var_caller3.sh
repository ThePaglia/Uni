#!/bin/bash

#	init_var_caller3.sh

VAR="contenutoiniziale"

# non cambia se lascio VAR locale
# o se la imposto come variab. di ambiente
# export VAR

echo "VAR = ${VAR}"

VAR="stringa"    ./init_var_called.sh

# non cambia se lo chiamo con source
# provarlo anche cosi’ 
# VAR="stringa" source /init_var_called.sh

echo "VAR = ${VAR}"

