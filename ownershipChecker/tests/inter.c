int* f;

int n (int* s, int* t) {
  free(t);
  f = s;
  int* r;
  r = s;
  return r;
}

void m () {
  int* u = malloc(16);
  if (u == null) return;
  int* v = malloc(16);
  if (v == null) {free(u); return;}
  int* w;
  w = n(u, v);
  free(w);
  int* x = malloc(16);
  if (x == null) return;
  int* y;
  y = x;
  int* z;
  z = n(x, y);
  return;
}