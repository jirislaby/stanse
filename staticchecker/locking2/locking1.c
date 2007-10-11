int doublelock() {
    int a;
    lock1();
    if (a) {
        lock1();
        a=a*a;
    }
    print(a);
    unlock1();
    return a;
}

int doubleunlock() {
    int a;
    lock2();
    if (a) {
        a=a*a;
        unlock2();
    }
    print(a);
    unlock2();
    return a;
}


int endwithlocked() {
    int a;
    kernel_lock();
    if (a) {
        a=a*a;
        return a;
    }
    print(a);
    kernel_unlock();
    return a;
}