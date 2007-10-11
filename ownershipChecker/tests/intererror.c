long int foo(int a, int *r, int *s, int b) {
  r = s;
  int *p;
  p = r;
  free(r);
  return a;
}

long int boo() {
  int *a;
  int *b;
  foo(x, a, b, y);
}
