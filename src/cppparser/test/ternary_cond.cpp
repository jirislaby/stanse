int f1(int i)
{
    return i > 89? 42: 43;
}

int f2(int i)
{
    throw 4;
}

int f3(int i)
{
    return i? i: throw 4;
}

void f4(int i)
{
    i? throw 3: throw 4;
}

int e1(int const & a, int const & b);

int const & f5(int const & lhs, int const & rhs)
{
    return e1(lhs, rhs)? lhs: rhs;
}

int f6(int const & lhs, int const & rhs)
{
    return e1(lhs, rhs)? lhs: rhs;
}
