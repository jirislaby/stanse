void f1(int x)
{
    do
    {
        x = 0;
    }
    while (x);
}

void f2(int x)
{
    do
    {
        if (x > 42)
            break;
    }
    while (x);
}

void f3(int x)
{
    do
    {
        if (x > 42)
            continue;
        x = 0;
    }
    while (x);
}
