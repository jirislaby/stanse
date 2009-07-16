#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <semaphore.h>

/**
 * Two types of deadlock occured:
 * A
 *  I) Thread one holds lock lock_c and waits for lock_b
 *  II) thread two holds lock_b and waits for lock_c
 *
 * B
 *  I)Thread one holds lock_c and lock_b and waits for lock_a
 *  II)Thread two holds lock_a and waits for releasing lock lock_b
 *  
 */

typedef unsigned long int pthread_t;

typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_a;
extern spinlock_t lock_b;
extern spinlock_t lock_c;
extern spinlock_t lock_e;
extern spinlock_t lock_f;

//-------------SOURCE CODE-----------------------
//
void lockMeA() {
    printf("Locking A");
    spin_lock(lock_a);
    spin_unlock(lock_a);
}

void func_b() {
    spin_lock(lock_b);
    pthread_t thread[1];
    pthread_create( &thread[1], NULL, func_a, NULL );
    spin_lock(lock_c);
}

void func_a() {
    spin_lock(lock_a);
    pthread_t thread[1];
    pthread_create( &thread[1], NULL, func_b, NULL );
    spin_lock(lock_b);
}

void func_c() {
    spin_lock(lock_c);
    spin_lock(lock_b);
    lockMeA();
    spin_unlock(lock_b);
    spin_unlock(lock_c);
    spin_lock(lock_e);
    spin_lock(lock_f);
    spin_unlock(lock_f);
    spin_unlock(lock_e);
    spin_lock(lock_f);
    spin_lock(lock_e);

}

int main( int argc, char *argv[]  ) {
    int index=0;
    pthread_t thread[3];
    while(index < 3) {
        if(g != NULL) {
//        pthread_create( &thread[index], NULL, func_b, NULL );
        continue;
        } else {
        pthread_create( &thread[index+1], NULL, func_a, NULL );
        pthread_create( &thread[index+2], NULL, func_c, NULL );

        }
        index = index+3;
    }

    return 0;
}
