#!/bin/bash

echo  'for con $* non quotato'
	for name in  $* ;  do
       echo "argomento ${name}" ;
	done

echo 'for con "$*" quotato'
	for name in  "$*" ;  do
	        echo "argomento ${name}" ;
	done

echo 'for con $@ non quotato'
	for name in  $@ ;  do
	        echo "argomento ${name}" ;
	done

echo 'for con "$@" quotato'
	for name in  "$@" ;  do
	        echo "argomento ${name}" ;
	done

