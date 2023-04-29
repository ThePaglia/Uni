#!/bin/bash

out=""
while read primo secondo resto; do
	out=${out}${secondo}
done < $1

echo ${out}
