package ex2015.a05.sol1;

import java.util.Collections;
import java.util.List;

public class CListFactoryImpl<X> implements CListFactory<X> {

    @Override
    public CList<X> emptyCList() {
        return consCList(Collections.emptyList());
    }

    @Override
    public CList<X> consCList(List<X> elements) {
        return new CListImpl<>(elements,0);
    }

}
