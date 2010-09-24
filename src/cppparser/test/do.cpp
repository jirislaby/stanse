void f1(int x)
{
    do
    {
        x = 0;
    }
    while (x);
}

void f2(int x)
{
    do
    {
        if (x > 42)
            break;
    }
    while (x);
}

void f3(int x)
{
    do
    {
        if (x > 42)
            continue;
        x = 0;
    }
    while (x);
}

struct s1
{
    s1(s1 const &);
    ~s1();

    operator bool();
};

s1 e1(int ch);

void f4(int *& p)
{
    do
    {
    }
    while (e1(*p));
}
