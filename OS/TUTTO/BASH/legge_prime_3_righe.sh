 #!/bin/bash

#find /usr/include/ -type f -exec head -n 3 '{}' \; 

find /usr/include -type f -exec head -n 3 '{}' \; | cut -b -3
