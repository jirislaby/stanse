struct s
{
    ~s();
};

void f1()
{
    s const & a = s();
}
