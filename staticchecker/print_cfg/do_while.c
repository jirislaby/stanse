int example1() {
    int a;
    a=100;
    do {
        print(a);
        a--;
    }  while (a>0);
    return a;
}

int example2() {
    int a,b;
    a=100;
    do {
        print(a);
        b=read();
        if (b==a) continue;
        else {
            if (b*b==a) break;
        }
        a--;
    } while (a>0);
    return a;
}