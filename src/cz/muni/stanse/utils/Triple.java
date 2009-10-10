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
public final class Triple<A,B,C> {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public Triple(final A first, final B second, final C third) {
        this.first = first;
        this.second = second;
        this.third = third;
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
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public C getThird() { return third; }

    public void setFirst(final A value) { first = value; }
    public void setSecond(final B value) { second = value; }
    public void setThird(final C value) { third = value; }

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
        result = PRIME * result + ((third == null) ? 0 : third.hashCode());
        return result;
    }

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
                false : isEqualWith((Triple<A,B,C>)obj);
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
    public boolean isEqualWith(final Triple<A,B,C> other) {
        return arePartsEqual(first, other.first) &&
		arePartsEqual(second, other.second) &&
		arePartsEqual(third, other.third);
    }

    public static <A,B,C> Triple<A,B,C> make(final A a, final B b, final C c) {
        return new Triple<A,B,C>(a,b,c);
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
    /**
     * @brief
     */
    private C third;
}
