#include "mutex.h"

struct s1
{
    virtual void foo(mutex & m) = 0;
};

struct s2 : s1
{
    void foo(mutex & m)
    {
        m.release();
    }
};

void foo(s1 * p)
{
    mutex m;
    m.acquire();
    p->foo(m);
    m.release();
}
