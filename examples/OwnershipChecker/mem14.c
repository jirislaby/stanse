long int foo(int s) {
  int *p;
  int i;
  for (i = 0; i < 10; i++) {
    p = malloc(16);
    if (p != null) free(p);
  }
  return p;
}
