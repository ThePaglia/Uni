#!/bin/bash

#	init_var_called.sh

echo "la variabile e' stata passata ? "

echo "VAR = ${VAR}"

# Verifico se nuova VAR e’ di ambiente
echo "env | grep VAR"
env | grep VAR

