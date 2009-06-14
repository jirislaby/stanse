struct lock_t
{
    int x;
};

void A(struct lock_t* (*fn)(int e))
{
    lock((*fn)(4)->x);
    B(fn,4);
}

void B(struct lock_t* (*fn)(int e), int q)
{
    unlock((*fn)(q)->x);
}

void C(int a, int b)
{
}
