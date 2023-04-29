package ex2015.a05.e1;

import java.util.List;

/**
 * A factory for immutable cyclic lists with element type X
 */
public interface CListFactory<X> {
    
    
    /**
     * @return an empty CList (that is, with 0 elements)
     */
    CList<X> emptyCList();
    
    /**
     * @return a CList with same content of elements
     */
    CList<X> consCList(List<X> elements);

}
