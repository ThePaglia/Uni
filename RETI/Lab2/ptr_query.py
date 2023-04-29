#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 17 15:36:08 2023

@author: apirodd
"""

from dns import reversename
from dns import resolver
domain_address = reversename.from_address('8.8.4.4')

print ("il domain address è:",(domain_address),"\n")

ip_address = reversename.to_address(domain_address)
print ("ip address è:", ip_address,"\n")


domain_name = str(resolver.resolve(domain_address,"PTR")[0])
print ("il nome di dominio è:", domain_name,"\n")