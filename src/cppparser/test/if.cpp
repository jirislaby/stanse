int f1(int x)
{
    if (x)
        return 42;
    return 43;
}

int f2(int x)
{
    if (x)
        return 42;
    else
        return 43;
}

int f3(int x)
{
    if (x)
        return 42;
    return x + 1;
}
