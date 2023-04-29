/* writeToShm.c */

#include <stdlib.h>
#include <unistd.h>   /* exit()  ftruncate() etc */
#include <stdio.h>
#include <string.h>   /* memset */

#include <sys/mman.h>  /* Memory MANagement: shm_* stuff, and mmap() */
#include <sys/stat.h>
#include <fcntl.h>     /* flags for shm_open */
#include <sys/types.h> /* ftruncate() */

/* Posix IPC object name [system dependant]
   L'oggetto "segmento in memoria condisa"
   viene fatto vedere, inizialmente, come fosse un file,
   ma un file in memoria, non su disco.
   Il nome dell'oggetto "foo1432" viene usato per
   creare il file /dev/shm/foo1423
   in un filesystem NON su disco ma in memoria.
   Con il reboot del sistema il file sparisce
   perche' non e' salvato su disco.
   Ciascun processo, poi, chiedera' di vedere
   quel segmento come se facesse parte del proprio
   spazio di indirizzamento, mediante la chiamata
   alla funzione mmap.
*/
#define SHMOBJ_PATH         "/foo1423"
/* maximum length of the content of the message */
#define SHARED_SEGMENT_SIZE 1024


int main(int argc, char *argv[]) {
    int shmfd, rc;
    int shared_seg_size = SHARED_SEGMENT_SIZE;
    char *shared_msg;      /* the pointer to the shared segment */
    
    /* creating the shared memory object.
    int shm_open(const char *name, int oflag, mode_t mode);
    oflags:
       O_CREAT    Create the shared memory object if it does not  exist.
       O_RDWR     Open the object for read-write access.
       O_RDONLY   Open the object for read access.
       O_EXCL     If  O_CREAT  was  also specified, and a shared memory object
                  with the given name already exists, return  an  error.   The
                  check  for  the existence of the object, and its creation if
                  it does not exist, are performed atomically.
       O_TRUNC    If the shared memory object already exists, truncate  it  to
                  zero bytes.
    mode:
       S_IRWXU    file owner has read, write and execute permission.
       S_IRWXG    group has read, write and execute permission.
       S_IRWXO    others have read, write and execute permission.
       S_IROTH    others have read permission.

    A new shared memory object  initially  has  zero  length.
    The size of the object can be set using ftruncate(2).
    Return Value: On successful completion  shm_open()  returns  a  new
               nonnegative file  descriptor referring to the shared memory object.
               On failure,  shm_open()  returns -1.
    */
    shmfd = shm_open(SHMOBJ_PATH, O_CREAT | O_EXCL | O_RDWR, S_IRWXU | S_IRWXG);
    if (shmfd < 0) {
        perror("shm_open() failed");
        exit(1);
    }
    fprintf(stdout, "Created shared memory object %s\n", SHMOBJ_PATH);
    
    /* adjusting mapped file size (make room for the whole segment to map) */
    rc = ftruncate(shmfd, shared_seg_size);
    if (rc != 0) {
        perror("ftruncate() failed");
        exit(1);
    }

    /* requesting the shared segment */    
    shared_msg = (char *)mmap(NULL, shared_seg_size, PROT_READ | PROT_WRITE, MAP_SHARED, shmfd, 0);
    if (shared_msg == MAP_FAILED /* is ((void*)-1) */ ) {
        perror("mmap() failed");
        exit(1);
    }
    fprintf(stdout, "Shared memory segment allocated correctly (%d bytes) at address %p\n", shared_seg_size, shared_msg );

    /* producing a message on the shared segment */
    memset(shared_msg, 0, shared_seg_size );
    snprintf(shared_msg, shared_seg_size, "\"This is the content of the shared object\"");

	/*
	sleep(2000);
	*/

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

    /* [uncomment if you wish] requesting the removal of the shm object.
        this causes no other processes may use the shm objetc */
/*
    if (shm_unlink(SHMOBJ_PATH) != 0) {
        perror("shm_unlink() failed");
        exit(1);
    }
*/

    return 0;
}


