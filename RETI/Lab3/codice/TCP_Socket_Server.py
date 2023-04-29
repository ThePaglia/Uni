# Corso di Programmazione di Reti - Laboratorio - UniversitÃ  di Bologna
# Socket_Programming_Assignment - WebServer - G.Pau - A. Piroddi

import sys
from socket import * 

serverSocket = socket(AF_INET, SOCK_STREAM)
server_address=('localhost',8080)
serverSocket.bind(server_address)

#listen(1) Definisce la lunghezza della coda di backlog, ovvero il numero
#di connessioni in entrata che sono state completate dallo stack TCP / IP
#ma non ancora accettate dall'applicazione.
serverSocket.listen(1)
print ('the web server is up on port:',8080)

while True:

    print ('Ready to serve...')
    connectionSocket, addr = serverSocket.accept()
    print(connectionSocket,addr)

    try:

        message = connectionSocket.recv(1024)
        if len(message.split())>0: 
            print (message,'::',message.split()[0],':',message.split()[1]) 
            filename = message.split()[1] 
            print (filename,'||',filename[1:]) 
            f = open(filename[1:],'r+') 
            outputdata = f.read()
            print (outputdata) 
                
     #Invia la riga di intestazione HTTP nel socket con il messaggio OK

            connectionSocket.send("HTTP/1.1 200 OK\r\n\r\n".encode())
            connectionSocket.send(outputdata.encode())
            connectionSocket.send("\r\n".encode())
            connectionSocket.close()
            

    except IOError:
 #Invia messaggio di risposta per file non trovato
        connectionSocket.send(bytes("HTTP/1.1 404 Not Found\r\n\r\n","UTF-8"))
        connectionSocket.send(bytes("<html><head></head><body><h1>404 Not Found</h1></body></html>\r\n","UTF-8"))
        connectionSocket.close()


