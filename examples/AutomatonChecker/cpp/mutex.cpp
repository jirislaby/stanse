#include "mutex.h"

int main()
{
    mutex m;
    
    error_t error = m.acquire();
    error = m.release();
    error = release(m);
}
