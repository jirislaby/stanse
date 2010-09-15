enum s1 { s1_1, s1_2, s1_3 };

void e1();
void e2(s1);

s1 f1()
{
    return s1_2;
}

void f2(s1 a)
{
    switch (a)
    {
    case s1_1:
        e1();
        break;
    default:
        e2(a);
    }
}
