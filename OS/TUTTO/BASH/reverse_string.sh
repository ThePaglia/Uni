#!/bin/bash

strlen=${#1}
for (( i=$strlen-1; i>=0; i-- )); do
    revstr=$revstr${1:$i:1}
done

echo ${revstr}
