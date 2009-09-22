void a()
{
	for (;0;)
		f0();
	for (;;)
		if (f1())
			break;
	for (0;;)
		if (f2())
			break;
	for (;1;)
		if (f3())
			break;
	for (;;2)
		if (f4())
			break;
	for (0; 1;)
		if (f5())
			break;
	for (0;; 2)
		if (f6())
			break;
	for (; x; 2)
		f7();
	for (0; x; 2) {
		f8();
		if (0)
			continue;
	}
}
