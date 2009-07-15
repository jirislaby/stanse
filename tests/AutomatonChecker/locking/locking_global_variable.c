int l = 0;

void A()
{
    B();
    C();
}

void B()
{
    lock(l);
}

void C()
{
    unlock(l);
}
