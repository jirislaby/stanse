long int foo(int *r, int *s) {
  int *t;
  int *v;
  t = malloc(16);
  if (t == null) return 1;
  v = t;
  s = t;
  r = s;
  int *p;
  int *q;
  p = q;
  p = r;
  free(r);
  return 0;
}
