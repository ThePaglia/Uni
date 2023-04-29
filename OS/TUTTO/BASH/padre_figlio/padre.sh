#!/bin/bash

trap "echo ricevuto SIGUSR2 ! ; exit " SIGUSR2

echo padre in esecuzione PID $$

./figlio.sh &
CHILDPID=$!
echo il padre vede come pid figlio ${CHILDPID}

while true ; do 
	echo "padre";
	sleep 1
done

