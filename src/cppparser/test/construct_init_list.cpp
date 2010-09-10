struct s1
{
    int i;

    s1(int x)
        : i(x)
    {
    }
};

int f1()
{
    s1 s(42);
    return s.i;
}

struct s2 : s1
{
    s2()
        : s1(65)
    {
    }
};

int f2()
{
    s2 s;
    return s.i;
}
