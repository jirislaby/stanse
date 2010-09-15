int f1(int * p)
{
    return p[42];
}

int * f2(int * p)
{
    return &p[42];
}

int f3(int (*p)[2])
{
    return (*p)[0];
}

int f4(int (*p)[2])
{
    return (*p)[1];
}
