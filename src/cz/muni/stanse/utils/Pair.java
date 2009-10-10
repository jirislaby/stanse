/**
 * @brief
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.utils;

/**
 * @brief
 *
 * @see
 */
public final class Pair<A,B> {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public A getFirst() { return first; }
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public B getSecond() { return second; }

    public void setFirst(final A value) { first = value; }
    public void setSecond(final B value) { second = value; }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((Pair<A,B>)obj);
    }

    static private <A> boolean arePartsEqual(A one, A two) {
	return (one == null && two == null) || (one != null && two != null &&
		one.equals(two));
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public boolean isEqualWith(final Pair<A,B> other) {
        return arePartsEqual(first, other.first) &&
		arePartsEqual(second, other.second);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((first == null) ? 0 : first.hashCode());
        result = PRIME * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    /**
     * 
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#toString()
     */
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

    public static <A,B> Pair<A,B> make(final A a, final B b) {
        return new Pair<A,B>(a,b);
    }

    // private section

    /**
     * @brief 
     */
    private A first;
    /**
     * @brief 
     */
    private B second;
}
