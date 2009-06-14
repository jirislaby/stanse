struct node_t
{
    int* x;
};

void A(struct node_t* (*fn)(int e))
{
    (*fn)(4)->x = (int*)malloc(sizeof(int));
    H(fn,4);
}

void H(struct node_t* (*fn)(int e), int q)
{
    B((*fn)(q));
}

void B(struct node_t* y)
{
    C(y->x);
}

void C(int* z)
{
    free(z);
}
