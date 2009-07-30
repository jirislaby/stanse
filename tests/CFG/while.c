void a()
{
	pre1();
	while (0) {
		foo1();
		if (0)
			break;
		bar1();
		if (0)
			continue;
		baz1();
	}
	post1();

	pre2();
	while (x) {
		foo2();
		if (x)
			break;
		bar2();
		if (x)
			continue;
		baz2();
	}
	post();
}
