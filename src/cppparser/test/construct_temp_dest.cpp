void e1();
void e2();
void e3();

struct s1
{
    s1()
    {
        e1();
    }
    
    ~s1()
    {
        e2();
    }
};

void f1()
{
    s1(), e3();
}
