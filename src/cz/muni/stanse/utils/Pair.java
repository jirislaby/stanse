package cz.muni.stanse.utils;

public final class Pair<A,B> {

    // public section

    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }

    @Override
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((Pair<A,B>)obj);
    }

    public boolean isEqualWith(final Pair<A,B> other) {
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((first == null) ? 0 : first.hashCode());
        result = PRIME * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append('{');
	if (first != null)
	    sb.append(first.toString());
	sb.append(':');
	if (second != null)
	    sb.append(second.toString());
	sb.append('}');
	return sb.toString();
    }

    // private section

    private final A first;
    private final B second;
}
