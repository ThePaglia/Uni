#!bin/bash
./estrai_asterischi.sh < input.txt &
PIDLANCIATO=$!
echo fatto &
wait ${PIDLANCIATO}
