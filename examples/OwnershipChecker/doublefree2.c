long int foo() {
  int *p;
  int *q;
  q = malloc(16);
  if (q == null) return 1;
  p = q;
  free(p);
  free(q);
  return 0;
}
