#!/bin/bash

NOMEFILE=temp.txt
rm -f ${NOMEFILE}
touch ${NOMEFILE}

( tail -f ${NOMEFILE} | ./A.sh | ./B.sh >> ${NOMEFILE} )

exit 0

