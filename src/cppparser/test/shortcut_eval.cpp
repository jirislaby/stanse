bool e1();
bool e2();

bool f1()
{
    return e1() && e2();
}

bool f2()
{
    return e1() || e2();
}
