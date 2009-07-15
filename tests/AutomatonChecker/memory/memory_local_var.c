void A()
{
    B();
}

void B()
{
    int* a;
    a = (int*)malloc(sizeof(int));
    free(a);
}
