
unlink /dev/shm/prodcons
ls -alh /dev/shm/

./ProcessiThread_NProdMCons1buffer_NONefficiente.exe C &
PIDCONSUMATORI=$!

sleep 1

./ProcessiThread_NProdMCons1buffer_NONefficiente.exe P &
PIDPRODUTTORI=$!

ls -alh /dev/shm/
sleep 10

kill -9 ${PIDCONSUMATORI}
kill -9 ${PIDPRODUTTORI}

wait ${PIDCONSUMATORI}
echo risultato wait $?
wait ${PIDPRODUTTORI}
echo risultato wait $?

unlink /dev/shm/prodcons

