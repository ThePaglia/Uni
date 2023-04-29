#!/bin/bash

lsof| grep mem | grep \.so | awk '{print $9}' | sort | uniq 
