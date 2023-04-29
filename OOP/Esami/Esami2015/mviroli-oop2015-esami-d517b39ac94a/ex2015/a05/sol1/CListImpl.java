package ex2015.a05.sol1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CListImpl<X> implements CList<X> {
    
    private List<X> elements;
    private int pos;
    
    CListImpl(List<X> elements, int pos) {
        this.elements = elements;
        this.pos = pos;
    }
    
    private int relativePos(int pos){
        return (pos+this.pos) % this.elements.size();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public X getElem(int pos) {
        return this.elements.get(this.relativePos(pos));
    }

    @Override
    public boolean contains(X x) {
        return this.elements.contains(x);
    }

    @Override
    public CList<X> add(X x, int pos) {
        int p = this.relativePos(pos);
        List<X> l = new LinkedList<X>(this.elements);
        l.add(p, x);
        return new CListImpl<>(l,p);
    }

    @Override
    public CList<X> shift(int pos) {
        return new CListImpl<>(this.elements,this.relativePos(pos));
    }

    @Override
    public List<X> toList() {
        List<X> l = new LinkedList<>(this.elements);
        Collections.rotate(l,-this.pos);
        return l;
    }

    @Override
    public String toString() {
        return "CListImpl [elements=" + elements + ", pos=" + pos + "]";
    }
    
    

}
