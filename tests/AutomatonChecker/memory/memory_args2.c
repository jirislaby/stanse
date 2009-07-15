struct S { int* x; };

void A(struct S* a)
{
    a->x = (int*)malloc(sizeof(int));
    B(a);
}

void B(struct S* b)
{
    free(b->x);
}
