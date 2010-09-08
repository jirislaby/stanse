void f1(int x)
{
    while (x)
        x = 0;
}

void f2(int x)
{
    while (x)
    {
        if (x < 42)
            break;
    }
}

void f3(int x)
{
    while (x > 10)
    {
        if (x < 42)
            continue;
        x = 0;
    }
}
