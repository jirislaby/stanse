typedef union {
  char __size[24];
  long int __align;
} spinlock_t;

extern spinlock_t lock_l;
int a,b,c;

int main( int argc, char *argv[]  ) {
	a = 0;
	if(b==1)
	{
		spin_lock(lock_l);
		a = 1; // lock
		spin_unlock(lock_l); // lock
	}
	else 
	{
		c = 0;
	}
	a = 2;
    return 0;
}
