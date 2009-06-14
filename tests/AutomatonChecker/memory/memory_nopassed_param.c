void A(int* x, int* y)
{
    x = (int*)malloc(sizeof(int));
    B(y);
    free(x);
}

void B(int* a)
{
    free(a);
}
