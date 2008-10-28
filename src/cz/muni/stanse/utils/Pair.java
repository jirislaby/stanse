package cz.muni.stanse.utils;

public final class Pair<A, B> {

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }

    private final A first;
    private final B second;

}
