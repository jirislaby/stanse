void A(int* x)
{
    x = (int*)malloc(sizeof(int));
    B(x);
}

void B(int* y)
{
    C(y);
}

void C(int* z)
{
    free(z);
    A(z);
}
