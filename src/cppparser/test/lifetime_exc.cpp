struct s
{
    s() {}
    ~s() {}
};

void f0()
{
    s a;
}

void f1()
{
    s a = s();
}

void f2()
{
    s a = (s(), s());
}
