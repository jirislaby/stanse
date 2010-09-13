int g1;

int & f1()
{
    return g1;
}

bool f2(int & i)
{
    return &i == &g1;
}

bool f3()
{
    return f2(f1());
}

int & f4()
{
    int i;
    return i;
}
