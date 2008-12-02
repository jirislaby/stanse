void foo()
{
    int* p;
    p = (int*)malloc(sizeof(int));
    *p = 1;
    a = v->f(*p + 2);
    free(p);
    free(p);
}
