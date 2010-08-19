typedef int error_t;

struct mutex
{
    mutex();
    ~mutex();

    error_t acquire();
    error_t release();
};

int main()
{
    mutex m;
    
    error_t error = m.acquire();
    error = m.release();
    error = m.release();
}