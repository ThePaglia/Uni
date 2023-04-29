#!/bin/bash

while read RIGA; do
	echo ${RIGA} | sed 's/\*/\*\\/g; s/\?/\?\\/g; s/\[/\[\\/g; s/\]/\]\\/g'
done

