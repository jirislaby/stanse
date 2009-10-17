void a(void)
{
	if (x) {
		for (;;)
			;
		puts("I'm unreachable");
	} else if (x) {
		return;;
	} else if (x) {
		switch (x) {
		case 0:
			return -1;
			break;
		}
	}
}

void b(void)
{
	for (;;) {
		break;
		puts("I'm unreachable");
	}
}

void c(void)
{
goto b;
	puts("I'm unreachable");
b:
	x();
	y();
	if (1)
		goto b;
	puts("I'm unreachable");
}
