typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_l;
int a,b;

void foo(spinlock_t lock) {
	int len = 0;
	spin_lock(lock);
	a = 4;
	b=1;
}

void bar(spinlock_t lock) {
	int len = 0;
	spin_unlock(lock);
}

int main( int argc, char *argv[]  ) {
	a = 0;
	foo(lock_l);
	a = 2;
	bar(lock_l);
	a = 1;
	return 0;
}
