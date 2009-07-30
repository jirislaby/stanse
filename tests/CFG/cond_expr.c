extern int m(), n();

void a()
{
	0 ? 1 : 2;

	0 ? m() : n();

	m() ? : n();
}
