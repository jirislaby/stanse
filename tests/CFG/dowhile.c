void a()
{
	pre();
	do {
		foo();
		if (0)
			break;
		bar();
		if (0)
			continue;
		baz();
	} while (0);
	post();
}
