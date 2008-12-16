/**
 * @brief
 *  
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
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((Triple<A,B,C>)obj);
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
        return first.equals(other.first) &&
               second.equals(other.second) &&
               third.equals(other.third);
    }

    // private section

    /**
     * @brief
     */
    private final A first;
    /**
     * @brief
     */
    private final B second;
    /**
     * @brief
     */
    private final C third;
}
