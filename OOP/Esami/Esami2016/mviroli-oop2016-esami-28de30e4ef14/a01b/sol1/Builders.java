package ex2016.a01b.sol1;

import java.util.Collection;

interface Builders {
    // Creates a builder of any list of elements
    <X> ListBuilder<X> makeBasicBuilder();

    // Creates a builder of only lists of given size
    <X> ListBuilder<X> makeBuilderWithSize(int size);

    // Creates a builder of only lists of elements taken from the argument
    <X> ListBuilder<X> makeBuilderFromElements(Collection<X> from);

    // Creates a builder of only lists of elements taken from the argument and with given size
    <X> ListBuilder<X> makeBuilderFromElementsAndWithSize(Collection<X> from, int size);
}