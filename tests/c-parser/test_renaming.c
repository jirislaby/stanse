int a, b1, a_4;
int x[5];

struct {
	int st_a;
} st;

void amain(int amain);

void amain(int amain)
{
	int a;
	{{{
	int b2;
/*	void b(int b2) {
		int a, b3;
	}*/
	}}}
	b(0); /* this acts as an undefined */
}

int xyz(a, b, c)
{
	return a + b + c;
}

void bmain(void)
{
	enum {
		XYZ,
	} st;
	int a[10];
/*	void bmain() {
	}*/
	bmain();
	a[0] = 0;
}
