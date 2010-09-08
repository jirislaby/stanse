void f1(int x)
{
    for (int i = 0; i < 42; i += 1)
    {
        x += 1;
        if (x == 4)
            break;
        if (x == 2)
            continue;
        x *= 2;
    }
}
