#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 17 09:01:12 2023

@author: apirodd
"""

import dns.resolver as dns

name = input("inserisci il realm (esempio_: unibo.it) di cui vuoi conoscere ipaddress \n")

#Record_type = ['A', 'AAAA', 'MX', 'NS', 'TXT', 'SOA']
Record_type = ['A', 'AAAA', 'MX']
for qtype in Record_type:
    answer = dns.resolve(name,qtype, raise_on_no_answer=False)
    if answer.rrset is not None:
        print(answer.rrset)
        
        