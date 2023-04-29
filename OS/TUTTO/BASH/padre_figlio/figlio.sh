#!/bin/bash

echo "figlio in esecuzione $$ suo padre e\' ${PPID} "
sleep 5
kill -s SIGUSR2 ${PPID}

