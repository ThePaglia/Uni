'''
Corso di Programmazione di Reti - Laboratorio - Università di Bologna
Socket_Programming_Assignment - WebServer - G.Pau - A. Piroddi

Per eseguire il presente codice è necessario utilizzare o una Command Prompt o dal tab Run selezionare Run...Customized

'''
import socket as sk
import sys

clientsocket = sk.socket(sk.AF_INET, sk.SOCK_STREAM)

if len(sys.argv) != 4:
    print (len(sys.argv))
    print("Il comando non è corretto. Usa il seguente formato: client.py indirizzo_ip_del_server_web porta_del_server nome_della_pagina_html")
    sys.exit(0)

host = str(sys.argv[1])
port = int(sys.argv[2])
request = str(sys.argv[3])
request = "GET /" + request + " HTTP/1.1 \r\n\r\n"
try:
    clientsocket.connect((host,port))
except Exception as data:
    print (Exception,":",data)
    print ("Ritenta sarai più fortunato.\r\n")
    sys.exit(0)
clientsocket.send(request.encode()) #se tutto è andato bene, il server risponde con un 200 OK
print(request.encode())   
response = clientsocket.recv(1024)
    
print (response)
    
clientsocket.close()



