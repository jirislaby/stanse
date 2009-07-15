void A()
{
    int a;

    lock(a);
    B(&a);
}

void B(int* b)
{
    unlock(*b);
}
