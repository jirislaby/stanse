void A(int* x)
{
    if (!x)
        x = (int*)malloc(sizeof(int));
    free(x);
}
