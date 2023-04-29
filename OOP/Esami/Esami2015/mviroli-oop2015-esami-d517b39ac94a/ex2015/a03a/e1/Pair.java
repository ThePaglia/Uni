package ex2015.a03a.e1;

/**
 * An umodifiable, generic Pair<X,Y>, with getters, hashCode, equals, and
 * toString well implemented.
 *
 * @param <X>
 *            the type of the first element
 * @param <Y>
 *            the type of the second element
 */
public final class Pair<X, Y> {

    private X x;
    private Y y;

    /**
     * @param x
     *            the first value
     * @param y
     *            the second value
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * @return first value
     */
    public X getX() {
        return x;
    }

    /**
     * @return second value
     */
    public Y getY() {
        return y;
    }
    
    /**
     * changes first value
     */
    public void setX(X x) {
        this.x = x;
    }

    /**
     * changes second value
     */
    public void setY(Y y) {
        this.y = y;
    }
    

    @Override
    public int hashCode() {
        return x.hashCode() ^ y.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Pair ? x.equals(((Pair<?, ?>) obj).x) && y.equals(((Pair<?, ?>) obj).y) : false;
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + ">";
    }

}
