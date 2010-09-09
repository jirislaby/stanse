struct s1
{
    int i;
    
    s1()
    {
        i = 42;
    }
};

int f1()
{
    return s1().i;
}
