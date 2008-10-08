long int foo(int s) {
  a=b;
  int *p;
  p = malloc(16);
  return p;
}

long int boo() {
  int *a;
  a = foo(c);
  if (a != null) free(a);
}
