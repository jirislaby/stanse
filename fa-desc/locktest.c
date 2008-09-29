int doublelock() {
    int a;
    lock();
    if (a) {
        lock();
        a=a*a;
    } else {
    print(a);
    print(a);
    print(a);
    print(a);
	lock();
	lock(a,b);
    print(a);
    print(a);
    print(a);

    }
    print(a);
    print(a);
    print(a);
    print(a);
    print(a);
    print(a);
    unlock();
    return a;
}

int doubleunlock() {
    int a;
    lock();
    if (a) {
        a=a*a;
        unlock();
    }
    print(a);
    unlock();
    return a;
}


int endwithlocked() {
    int a;
    lock();
    if (a) {
        a=a*a;
        return a;
    }
    print(a);
    unlock();
    return a;
}

