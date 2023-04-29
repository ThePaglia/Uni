#!/bin/bash

FILES=`find /usr/include/linux/ -name "*.h" -print`
for NAME in ${FILES} ; do
	grep int ${NAME} | cut --bytes=-3
done

