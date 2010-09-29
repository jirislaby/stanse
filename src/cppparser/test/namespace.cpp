namespace ns1 {

int e1(int x);

namespace {

int e1(long x);

int f1()
{
    return e1(42);
}

using ns1::e1;

int f2()
{
    return e1(42);
}

}

}

using namespace ns1;

int f3()
{
    return e1(42) + e1(42L);
}

static int f4()
{
    return 42;
}

namespace {

int f4()
{
    return 43;
}

}

extern "C" int e2();

int f5()
{
    return e2();
}

static int v1;

int f6()
{
    return ++v1;
}
