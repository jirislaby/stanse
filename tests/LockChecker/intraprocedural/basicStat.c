typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_l;
int a,b;

int main( int argc, char *argv[]  ) {
	b = 0;
	a = 0;
	spin_lock(lock_l);
	a = 1; // lock
	a = b;
	a = 2;
	a = 3;
	a = 4;
	spin_unlock(lock_l); // lock
	b = 2;
    return 0;
}
