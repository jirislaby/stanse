void foo() {}

void test1()
{
        foo();
    lock(a);
    lock(b);
        foo();
        if (1)
        {
            foo();
            lock(a);
        }
        else
        {
            foo();
        }
        foo();
        foo();
    islocked(a);
        foo();
        foo();
    unlock(a);
        foo();
        while (1)
        {
            foo();
            foo();
        }
    unlock(a);
    unlock(b);
        foo();
    islocked(b);
}

void test2()
{
    if (1) lock(a);
    if (1) lock(b);
    if (1) unlock(a);
    if (1) unlock(b);
}
