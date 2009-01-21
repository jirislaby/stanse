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
    if (1)
        lock(a);
    if (1)
        lock(b);
    if (1)
        unlock(a);
    if (1)
        unlock(b);
}

void test3()
{
    foo();

    if (trylock(&a))
        unlock(&a);
    else
        unlock(&a);

    foo();

    if (!trylock(&b))
        unlock(&b);
    else
        unlock(&b);

    foo();

    if (trylock(&c) == 0)
        unlock(&c);
    else
        unlock(&c);

    foo();

    if (trylock(&d) != 0)
        unlock(&d);
    else
        unlock(&d);

    foo();

    if (0 == trylock(&e))
        unlock(&e);
    else
        unlock(&e);

    foo();

    if (0 != trylock(&f))
        unlock(&f);
    else
        unlock(&f);

    foo();
}
