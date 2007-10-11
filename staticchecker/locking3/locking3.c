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
    lock(b);
    if (a) {
        a=a*a;
        unlock(b);
    }
    print(a);
    unlock(b);
    return a;
}


int endwithlocked() {
    int a;
    lock(x);
    if (a) {
        a=a*a;
        return a;
    }
    print(a);
    unlock(x);
    return a;
}