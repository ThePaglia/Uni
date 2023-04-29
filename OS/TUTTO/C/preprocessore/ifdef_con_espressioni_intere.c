#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void)
{
        #if 500/10  > 100*(3+4) 
                printf("espressione vera\n");
        #else
                printf("espressione falsa\n");
        #endif

        return(1);
}


