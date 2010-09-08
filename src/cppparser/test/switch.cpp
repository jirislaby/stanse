int f1(int x)
{
    switch (x)
    {
        return 0;
    case 42:
        return 42;
    default:
        return 1;
    }
}

int f2(int x)
{
    switch (x)
    {
    case 1:
    case 2:
        x += 1;
    case 3:
        x *= 2;
        break;
    case 4:
        x -= 85;
    }
    
    return x;
}
