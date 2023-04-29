#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Creato on Wed Mar 22 11:43:51 2023

@author: apirodd
"""
import smtplib
import email.utils
from email.mime.text import MIMEText

# Creiamo il messaggio mail
msg = MIMEText('Questo è il corpo del messaggio.')
msg['To'] = email.utils.formataddr(('DESTINATARIO', 'destinatario@example.com'))
msg['From'] = email.utils.formataddr(('MITTENTE', 'mittente@example.com'))
msg['Subject'] = 'Prova Invio Mail'

server = smtplib.SMTP('127.0.0.1', 1027)

server.set_debuglevel(True) # mostriamo le comunicazioni con il server
try:
    server.sendmail('mittente@example.com', ['destinatario@example.com'], msg.as_string())
finally:
    server.quit()