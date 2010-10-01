typedef int error_t;

struct mutex
{
    mutex();
    ~mutex();

    error_t acquire();
    error_t release();
};

inline error_t release(mutex & m)
{
    return m.release();
}
