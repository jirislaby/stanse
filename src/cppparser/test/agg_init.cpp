struct s2
{
    int a;
    int b;
};

struct s1
{
    int a;
    s2 b;
    int c;
};

void f1()
{
    s1 a = { 47, {8}, 4 };
    s1 b = { 47, {}, 4 };
    s1 c = {};
}

void f2()
{
    int a[2][2] = { 4, 5, 6 };
}

void f3()
{
    s2 a[1];
    s2 b[1] = {};
}

void f4()
{
    const int debruijn[32] = 
    {
        0, 0, 3, 0, 3, 1, 3, 0, 3, 2, 2, 1, 3, 2, 0, 1, 
        3, 3, 1, 2, 2, 2, 2, 0, 3, 1, 2, 0, 1, 0, 1, 1
    };
}

int * f5(int (&p)[45])
{
    return &p[0];
}

int f6()
{
    int a[] = { 2, 3, 9, 6, 42 };
    return a[4];
}

int f7()
{
    typedef int array_type[4];
    array_type a = { 1, 2, 3, 4 };
    return a[2];
}

int f8()
{
    char a[] = "abcd";
    char b[1000] = "efgh";

    wchar_t c[] = L"abcd";
    wchar_t d[1000] = "efgh" L"ijkl";

    return c[2];
}
