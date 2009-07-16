#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <semaphore.h>
/**
 *  Deadlock occured:
 *  ----------------
 *  Thread igmp_heard_query locks in_lock (line 44) and waits for im_lock
 *  Thread inet_select_addr locks inetdev_lock (line 34) and waits for in_lock
 *  Thread igmp_timer_expire calls lockMe and there is locked im_lock (line 20)  *   and thread waits for inetdev_lock
 *
 */

typedef unsigned long int pthread_t;

void lockMe() {
    spin_lock(&im_lock);
    im->tm_running=0;
    spin_lock(&inetdev_lock);
    
    spin_unlock(&inetdev_lock);
    spin_unlock(&im_lock);

}

void igmp_timer_expire(void *data) {
    struct ip_mc_list *im=(struct ip_mc_list *)data;
    int err;
    lockMe();
}

void inet_select_addr(void *arg) {
    spin_lock(&inetdev_lock);
    spin_lock(&in_lock);
    spin_unlock(&in_lock);
    spin_unlock(&inetdev_lock);
}

void igmp_heard_query(void *arg) {
    struct in_device *in = glob->in_dev->mc_list;
    struct ip_mc_list *im = glob->im;    

    spin_lock(&in_lock);
    spin_lock(&im_lock);
    spin_unlock(&im_lock);
    spin_unlock(&in_lock);
}



//-------------SOURCE CODE------------------------

int main( int argc, char *argv[]  ) {
    struct ip_mc_list mc_list;
    struct in_device device;
    struct global g;
    g.im = &mc_list;
    g.in_dev = &device;
    int index=0;
    while(g != NULL) {
        index++;
    }


    pthread_t thread[3];

    if(g != NULL) {
    pthread_create( &thread[1], NULL, igmp_heard_query, (void *) &g );
    pthread_create( &thread[2], NULL, inet_select_addr, (void *) &g );
    pthread_create( &thread[3], NULL, igmp_timer_expire, (void *) &mc_list );
    } else {
    pthread_create( &thread[1], NULL, igmp_heard_query, (void *) &g );
    pthread_create( &thread[2], NULL, inet_select_addr, (void *) &g );
    }

    return 0;
}
