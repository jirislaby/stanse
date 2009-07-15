void A()
{
    B();
}

void B()
{
    int a;

    lock(a);
    unlock(a);
}
