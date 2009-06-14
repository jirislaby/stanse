void A(int* x)
{
    if (x != 0)
        free(x);
    x = (int*)malloc(sizeof(int));
    A(x);
}
