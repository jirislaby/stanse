int *f;

long int foo(int s) {
  int *p;
  p = malloc(16);
  if (p == null) return 1;
  f = p;
  free(f);
  return 0;
}
