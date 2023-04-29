#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr  1 18:26:35 2023
LABORATORIO DI PROGRAMMAZIONE DI RETI
@author: apirodd
"""

"""
Client that sends the file (uploads)
"""
import socket
import tqdm
import os
import argparse
from Crypto.Cipher import AES
key = b"TheBestRandomKey"
nonce = b"TheBestRandomNce"
# creiamo la cifratura

cipher = AES.new(key, AES.MODE_EAX, nonce)

SEPARATOR = "<SEPARATOR>"
BUFFER_SIZE = 1024 * 4 #4KB

def send_file(filename, host, port):
    # recuperiamo la dimensione del file
    filesize = os.path.getsize(filename)
    # creiamo il socket del client
    s = socket.socket()
    print(f"[+] Connecting to {host}:{port}")
    s.connect((host, port))
    print("[+] Connected.")

    # inviamo il nome del file e la dimensione del file
    s.send(f"{filename}{SEPARATOR}{filesize}".encode())

    # cominciamo ad inviare il file
    progress = tqdm.tqdm(range(filesize), f"Sending {filename}", unit="B", unit_scale=True, unit_divisor=1024)
    with open(filename, "rb") as f:
        while True:
            # leggiamo i bytes dal file
            bytes_read = f.read(BUFFER_SIZE)
            if not bytes_read:
                # la trasmissione del file è completata
                break
            # utilizziamo sendall per garantire la trasmissione 
            # in reti trafficate
            # criptiamo i dati
            encrypted = cipher.encrypt(bytes_read)
            s.sendall(encrypted)
            # aggiorniamo la barra di avanzamento
            progress.update(len(bytes_read))
        s.send(b"<END>")
    # chiudiamo il socket
    s.close()

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Simple File Sender")
    parser.add_argument("file", help="File name to send")
    parser.add_argument("host", help="The host/IP address of the receiver")
    parser.add_argument("-p", "--port", help="Port to use, default is 5001", default=5001)
    args = parser.parse_args()
    filename = args.file
    host = args.host
    port = args.port
    send_file(filename, host, port)
