package cz.muni.stanse.utils;

public final class Trinity<A,B,C> {

    // public section

    public Trinity(final A first, final B second, final C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }
    public C getThird() { return third; }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((first == null) ? 0 : first.hashCode());
        result = PRIME * result + ((second == null) ? 0 : second.hashCode());
        result = PRIME * result + ((third == null) ? 0 : third.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((Trinity<A,B,C>)obj);
    }

    public boolean isEqualWith(final Trinity<A,B,C> other) {
        return first.equals(other.first) &&
               second.equals(other.second) &&
               third.equals(other.third);
    }

    // private section

    private final A first;
    private final B second;
    private final C third;
}
