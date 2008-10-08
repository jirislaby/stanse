int petka() {
     return 5;
}

int main() {
     int (*ahoj)();

     ahoj=petka;

     return (*ahoj)();
}

