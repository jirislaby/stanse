#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <semaphore.h>

/**
 * Deadlock warning with 4 parallel threads deadlock can occured
 * A -> B -> C -> D -> A
 *
 *
 *
 */


void threadA() {
    spin_lock(A);
    spin_lock(B);
    spin_unlock(B);
    spin_unlock(A);

    
    spin_lock(C);
    spin_lock(D);
}

void threadB() {
    spin_lock(B);
    spin_lock(C);
    spin_unlock(C);
    spin_unlock(B);

    spin_lock(D);
    spin_lock(A);
}

void threadC() {
    spin_lock(D);
    spin_lock(A);
}

void igmp_heard_query(void *arg) {

    spin_lock(&barrier);
    spin_lock(&in_lock);
    spin_lock(&im_lock);
    spin_unlock(&im_lock);
    spin_unlock(&in_lock);
    spin_unlock(&barrier);
}



//-------------SOURCE CODE------------------------

int main( int argc, char *argv[]  ) {
    struct ip_mc_list mc_list;
    struct in_device device;
    struct global g;

    pthread_create( &thread[1], NULL, threadA, (void *) &g );
    pthread_create( &thread[2], NULL, threadB, (void *) &g );
    

    return 0;
}
