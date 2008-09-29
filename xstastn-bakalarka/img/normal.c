int main() {
	one();
	two();
	three();
return 0;
}
int one() {
	two();
}
two() {
	main();
	two();
}

