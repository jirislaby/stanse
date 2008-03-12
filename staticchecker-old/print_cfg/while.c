int example1() {
    int a;
    a=100;
    while (a>0) {
        print(a);
        a--;
    }
    return a;
}

int example2() {
    int a,b;
    a=100;
    while (a>0) {
        print(a);
        b=read();
        if (b==a) continue;
        else {
            if (b*b==a) break;
        }
        a--;
    }
    return a;
}