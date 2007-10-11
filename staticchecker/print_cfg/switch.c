example1() {
    int n,a,b;
    n=3;
    switch (n) {
        case 0: a++;
        case 1: a+=b; break;
        case 2:
        case 3: a*=b;
        default: puts("blabla");
        a++;
    }
}

example2() {
    int n;
    n=3;
    switch (n) {
        case 0: puts("0"); break;
        case 1: puts("1"); break;
        case 2: puts("2"); break;
        case 3: puts("3"); break;
        case 4: puts("4"); break;
        case 5: puts("5"); break;
        case 6: puts("6"); break;
        default: puts("moc");
    }
}