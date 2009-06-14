struct lock_t
{
    int x;
};

void A(struct lock_t* (*fn)(int e))
{
    lock((*fn)(4)->x);
    H(fn,4);
}

void H(struct lock_t* (*fn)(int e), int q)
{
    B((*fn)(q));
}

void B(struct lock_t* y)
{
    C(y->x);
}

void C(int z)
{
    unlock(z);
}
