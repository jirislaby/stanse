typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_l;
int a,b,c;

int main( int argc, char *argv[]  ) {
	a = 0;
	while(b==1) // no lock | lock
	{
		spin_lock(lock_l); // no lock | lock
		a = 1; // lock		
	}
	a = 2; // no lock | lock
	spin_unlock(lock_l); // no lock | lock
    return 0;
}
