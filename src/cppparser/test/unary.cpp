int f1(int * i)
{
    return *i;
}

int f2(int i)
{
    return f1(&i);
}

int f3(int & i)
{
    return ++i;
}

int f4(int & i)
{
    return i++;
}

int f5(int i)
{
    return -i;
}

int f6(int i)
{
    return +i;
}
