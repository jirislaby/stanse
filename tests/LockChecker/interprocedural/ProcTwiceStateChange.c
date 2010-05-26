typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_l;
int a,b;

void foo() {
	a = 1;
}

int main( int argc, char *argv[]  ) {
	a = 0;
	foo();
	a = 2;
	spin_lock(lock_l);
	foo();
	spin_unlock(lock_l);
    return 0;
}
