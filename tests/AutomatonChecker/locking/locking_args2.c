struct S { int* x; };

void A(struct S* a)
{
    lock(a->x);
    B(a);
}

void B(struct S* b)
{
    unlock(b->x);
}
