int c1();
int c2(int);

int f1(int val)
{
    int res;
    if (val)
        res = c2(val);
    else
        res = c1();
    return res;
}

void f2(int *& p)
{
    c2(*p);
}
