void A(int x, int y)
{
    lock(x);
    B(y);
    unlock(x);
}

void B(int a)
{
    unlock(a);
}
