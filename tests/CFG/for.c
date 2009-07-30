void a()
{
/*	for (;;)
		f1();
	for (0;;)
		f2();
	for (;1;)
		f3();
	for (;;2)
		f4();
	for (0; 1;)
		f5();
	for (0;; 2)
		f6();*/
	for (; x; 2)
		f7();
	for (0; x; 2) {
		f8();
		if (0)
			continue;
	}
}
