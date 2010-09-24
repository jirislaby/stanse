void f1(int x)
{
    for (int i = 0; i < 42; i += 1)
    {
        x += 1;
        if (x == 4)
            break;
        if (x == 2)
            continue;
        x *= 2;
    }
}

struct s1
{
    s1();
    s1(s1 const &);
    ~s1();

    operator bool();
    s1 operator++();
};

s1 e1(int ch);

void f2(int *& p)
{
    for (s1 a; e1(*p); ++a)
    {
    }
}
