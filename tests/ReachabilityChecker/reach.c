void a(void)
{
	for (;;)
		;

	puts("I'm unreachable");
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
