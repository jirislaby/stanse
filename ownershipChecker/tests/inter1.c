int* f;

int n(int* s, int* t) {
  free(t);
  f = s;
  int* r;
  r = s;
  return r;
}

int main() {
  int* u = malloc(16);
  if (u == null) return 1;
  int* v = malloc(16);
  if (v == null) {free(u); return 1;}
  int* w;
  w = n(u, v);
  free(f);
  int* x = malloc(16);
  if (x == null) return;
  int* y;
  y = x;
  int* z;
  z = n(x, y);
  free(z);
  return 0;
}