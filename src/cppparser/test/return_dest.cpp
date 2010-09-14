struct s
{
    ~s();
};

int f1()
{
    s a;
    for (;;)
        return 1;
    return 0;
}
