int example1() {
    int a;
    for (a=0;a<100;a++) {
        print(a);
    }
    return a;
}

int example2() {
    int a,b;
    for (a=0;a<100;a++) {
        print(a);
        b=read();
        if (b==a) continue;
        else {
            if (b*b==a) break;
        }
    }
    return a;
}