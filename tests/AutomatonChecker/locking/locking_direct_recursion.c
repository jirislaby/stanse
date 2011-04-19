void A(int x)
{
    if (islocked(x))
        unlock(x);
    lock(x);
    A(x);
}
