long int foo(int s) {
  int *p;
  p = malloc(16);
  if (p != null) free(p);
  return p;
}

long int boo() {
  int *a;
  a = foo(c);
}
