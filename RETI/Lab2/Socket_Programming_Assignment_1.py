# Corso di Programmazione di Reti - Laboratorio - Università  di Bologna
# Socket_Programming_Assignment - WebServer - G.Pau - A. Piroddi


from socket import * #importiamo la <class 'type'> invece dell'intero modulo

# assegniamo alla porta del nostro server un valore a piacere es:82
serverPort=8080
# crea un socket INET di tipo STREAM
serverSocket = socket(AF_INET, SOCK_STREAM)
# associa il socket alla porta scelta
serverSocket.bind(('',serverPort))

#listen(1) Definisce la lunghezza della coda di backlog, ovvero il numero di connessioni in entrata che 
#sono state completate dallo stack TCP / IP
#ma non ancora accettate dall'applicazione.
#Non ha nulla a che fare con il numero di connessioni simultanee che il server può gestire.
serverSocket.listen(1)


print ('the web server is up on port:',serverPort)

while True:

    print ('Ready to serve...')
    #Stabilisce la connessione, ossia sul socket si prepara ad accettare connessioni in entrata all'indirizzo 
    #e porta definiti
    connectionSocket, addr = serverSocket.accept()
    print(serverSocket.accept())
    try:

        message = connectionSocket.recv(1024) ## riceve il messaggio di richiesta dal client
        if len(message.split())>0: #controlla che il messaggio non sia vuoto
            print (message,'::',message.split()[0],':',message.split()[1]) #stampa a video il messaggio di richiesta del client GET...
            filename = message.split()[1] #Il metodo split () divide una stringa in un elenco.
            print (filename,'||',filename[1:]) ## poiché la seconda parte dell'intestazione HTTP include un '/', questo indica di leggere dal secondo carattere espresso attraverso '[1:]'
            f = open(filename[1:],'r+') #il server apre il file
            outputdata = f.read() #bufferizza l'intero contenuto del file richiesto in un buffer temporaneo
            print (outputdata) #stampa a video
                
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
serverSocket.close()
connectionSocket.close()
sys.exit() ## Termina il programma dopo aver inviato i dati corrispondenti
