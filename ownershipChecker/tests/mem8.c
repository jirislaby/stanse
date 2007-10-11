long int foo(int *r, int *s) {
  a=b;
  r = s;
  int *p;
  p = r;
  free(r);
  return 0;
}
