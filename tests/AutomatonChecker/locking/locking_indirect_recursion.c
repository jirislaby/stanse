void A(int x)
{
    lock(x);
    B(x);
}

void B(int y)
{
    C(y);
}

void C(int z)
{
    unlock(z);
    A(z);
}
