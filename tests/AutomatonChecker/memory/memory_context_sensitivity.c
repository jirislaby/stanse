void A(int* x)
{
    x = (int*)malloc(sizeof(int));
    C(x,0);
}

void B(int* y)
{
    C(0,y);
    free(y);
}

void C(int* a, int* b)
{
    free(a);
    b = (int*)malloc(sizeof(int));
}
