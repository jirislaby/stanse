void A(int* x)
{
    x = (int*)malloc(sizeof(*x));
    B(x);
    free(x);
}

void B(int* y)
{
    y = (int*)malloc(sizeof(*y));
    free(y);
}

/*
void sizeOf()
{
    x = (int*)malloc(sizeof(*x));
}

void foo()
{
    int* p;
    free(p);
    p = (int*)malloc(sizeof(int));
    foo();
    p = (int*)malloc(sizeof(int));
    if (p == 0)
    {
        foo();
        if (!p)
            foo();
        free(p);
        foo();
        free(p);
        return;
    }
    f(p->x);
    if (p)
        foo();
    p = (int*)malloc(sizeof(int));
    foo();
}

void comparation1()
{
    int* p;

    p = (int*)malloc(sizeof(int));
    if (p)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if (!p)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if (p == 0)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if (p == (int*)0)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if (p != 0)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if (p != (int*)0)
        free(p);
    else
        free(p);
}

void comparation2()
{
    int* p;

    p = (int*)malloc(sizeof(int));
    if (0 == p)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if ((int*)0 == p)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if (0 != p)
        free(p);
    else
        free(p);

    p = (int*)malloc(sizeof(int));
    if ((int*)0 != p)
        free(p);
    else
        free(p);
}

void comparation3()
{
    p->x = (int*)malloc(sizeof(int));
    if (0 == p->x)
        free(p->x);
    else
        free(p->x);

    p[i] = (int*)malloc(sizeof(int));
    if (p[i])
        free(p[i]);
    else
        free(p[i]);
}

void deref1()
{
    int* p;
    p = (int*)malloc(sizeof(int));
    if (p == 0)
    {
        *p = 1;
        p[3] = 2;
        p->x = 3;
        a = f(p->x + p[3]);
        return;
    }
    *p = 1;
    p[3] = 2;
    p->x = 3;
    a = f(p->x + p[3]);
    free(p);
}
*/
