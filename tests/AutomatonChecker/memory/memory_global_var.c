int* a;

void A()
{
    B();
    C();
}

void B()
{
    a = (int*)malloc(sizeof(int));
}

void C()
{
    free(a);
}
