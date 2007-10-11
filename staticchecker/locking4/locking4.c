int doublelock() {
    int a;
    lock(a);
    if (a) {
        lock(a);
        a=a*a;
    }
    print(a);
    unlock(a);
    return a;
}

int doubleunlock() {
    int a;
    mylock3(b);
    if (a) {
        a=a*a;
        unlock3(b);
    }
    print(a);
    myunlock3(b);
    return a;
}


int endwithlocked() {
    int a;
    kernel_lock(x);
    if (a) {
        a=a*a;
        return a;
    }
    print(a);
    kernel_unlock(x);
    return a;
}