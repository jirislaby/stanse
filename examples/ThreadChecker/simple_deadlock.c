#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <semaphore.h>

/**
 * Deadlock occured:
 * --------------------
 *
 * Deadlock I)
 *  Thread I is at line 45, holds lock_c and requires lock_a
 *  Thread II is at line 38, holds lock_a, lock_b and requires lock_c
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

void func_b() {
    int index = 1;
    if(1 > index) {
    spin_lock(lock_a);
    spin_lock(lock_b);
    spin_lock(lock_c);

    spin_unlock(lock_c);
    spin_unlock(lock_b);
    spin_unlock(lock_a);
    } else {

    spin_lock(lock_c);
    spin_lock(lock_a);
    spin_unlock(lock_a);
    spin_unlock(lock_c);
    }
    spin_lock(lock_d);
    spin_lock(lock_e);
    spin_unlock(lock_e);
    spin_unlock(lock_d);

}

void func_a() {
    spin_lock(lock_c);
    spin_lock(lock_b);
    spin_lock(lock_a);
}

int main( int argc, char *argv[]  ) {
    int index=0;
    pthread_t thread[3];
    while(index < 3) {
        pthread_create( &thread[index], NULL, func_b, NULL );
        pthread_create( &thread[index], NULL, func_b, NULL );
        index++;
    }

    return 0;
}
