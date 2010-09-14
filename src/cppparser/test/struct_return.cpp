struct s
{
    s();
    s(s const &);
    ~s();
};

s f1()
{
    return s();
}

s f2()
{
    s a;
    return a;
}

s f3()
{
    s a, b;
    return a;
}
