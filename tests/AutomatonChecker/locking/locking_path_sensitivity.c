void A(int x)
{
    if (!islocked(x))
        lock(x);
    unlock(x);
}
