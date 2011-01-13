#include "mutex.h"

void may_throw();

void foo(mutex & m)
{
    m.acquire();
    may_throw();
    m.release();
}

void bar()
{
    mutex m;
    m.acquire();
    may_throw();
    m.release();
}
