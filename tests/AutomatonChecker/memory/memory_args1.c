void A()
{
    int* a;

    a = (int*)malloc(sizeof(int));
    B(&a);
}

void B(int** b)
{
    free(*b);
}
