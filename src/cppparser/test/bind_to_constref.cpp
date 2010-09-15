int e1(int const & a);

int f1()
{
    return e1(42);
}

int f2()
{
    return e1(e1(42));
}
