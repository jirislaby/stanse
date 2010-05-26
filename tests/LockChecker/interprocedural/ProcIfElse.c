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
	if(a == 0) {
		spin_lock(lock_l);
		foo();
		spin_unlock(lock_l);
	} else {
		a = 2;
	}
    return 0;
}
