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
