long void foo() {
  int *p;
  int *q;
  q = malloc(16);
  p = q;
  free(p);
}
