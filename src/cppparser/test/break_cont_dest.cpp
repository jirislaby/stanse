struct s
{
    s();
    s(s const &);
    ~s();
};

int e1();

void f1()
{
    for (;;)
    {
        s b;
        if (e1())
            continue;
        for (;;)
        {
            s a;
            if (e1())
                break;
        }
    }
}
