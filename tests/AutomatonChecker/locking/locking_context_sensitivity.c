void A(int x)
{
    lock(x);
    C(x,0);
}

void B(int y)
{
    C(0,y);
    unlock(y);
}

void C(int a, int b)
{
    unlock(a);
    lock(b);
}
