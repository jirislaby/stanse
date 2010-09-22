void f1(int x)
{
    while (x)
        x = 0;
}

void f2(int x)
{
    while (x)
    {
        if (x < 42)
            break;
    }
}

void f3(int x)
{
    while (x > 10)
    {
        if (x < 42)
            continue;
        x = 0;
    }
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
    while(e1(*p))
    {
    }
}
