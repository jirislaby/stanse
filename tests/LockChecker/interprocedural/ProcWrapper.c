typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_l;
int a,b;

void foo() {
	spin_lock(lock_l);
}

void bar() {
	spin_unlock(lock_l);
}

int main( int argc, char *argv[]  ) {
	a = 0;
	foo();
	a = 2;
	bar();
    return 0;
}
