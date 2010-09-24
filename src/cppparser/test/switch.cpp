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

int f3(int x)
{
    switch (x)
    {
    case (1+96-45)/2:
        x += 1;
    case 3:
        x *= 2;
        break;
    default:
        x += 45;
    case 44 - 4:
        x -= 85;
    }
    return x;
}
