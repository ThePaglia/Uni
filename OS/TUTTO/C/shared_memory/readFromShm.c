/* readFromShm.c */

#include <stdlib.h>
#include <unistd.h>   /* exit() etc */
#include <stdio.h>
#include <string.h>   /* memset */
#include <sys/mman.h>  /* Memory MANagement: shm_* stuff, and mmap() */
#include <sys/types.h>
/* 
#include <sys/ipc.h> 
#include <sys/shm.h> 
*/
#include <fcntl.h>
#include <sys/stat.h>

/* Posix IPC object name [system dependant] */
#define SHMOBJ_PATH         "/foo1423"
/* maximum length of the content of the message */
#define SHARED_SEGMENT_SIZE 1024


int main(int argc, char *argv[]) {
    int shmfd;
    int shared_seg_size = SHARED_SEGMENT_SIZE;
    char *shared_msg;      /* the pointer to the shared segment */
   
    /* creating the shared memory object 
       notare che qui non ho messo i flag O_CREAT | O_EXCL
       che invece ho usato per creare ex-novo l'oggetto nel writer
    */
    shmfd = shm_open(SHMOBJ_PATH, O_RDWR, S_IRWXU | S_IRWXG);
    if (shmfd < 0) {
        perror("shm_open() failed");
        exit(1);
    }
    printf("Created shared memory object %s\n", SHMOBJ_PATH);
    
    /* requesting the shared segment  */    
    shared_msg = (char *)mmap(NULL, shared_seg_size, PROT_READ | PROT_WRITE, MAP_SHARED, shmfd, 0);
    if (shared_msg == MAP_FAILED /* is ((void*)-1) */ ) {
        perror("mmap() failed");
        exit(1);
    }
    printf("Shared memory segment allocated correctly (%d bytes) at address %p\n", shared_seg_size, shared_msg );

    printf("The content of the shared memory is: %s\n", shared_msg );
    
    /* impedisco al processo di accedere ancora al segmento condiviso,
       ma l'oggetto condiviso rimane per essere acceduto da altri processi.
       Se il processo non fa la munmap, la munmap viene fatta dal
       sistema operativo quando il processo termina.
       Quindi nell'esempio serve a poco perche' il processo tra poco termina.
    */
    if ( munmap( shared_msg, shared_seg_size ) != 0) {
        perror("munmap() failed");
        exit(1);
    }

    /* requesting the removal of the shm object */
    if (shm_unlink(SHMOBJ_PATH) != 0) {
        perror("shm_unlink() failed");
        exit(1);
    }

    return 0;
}


