package ex2016.a03a.sol1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;


/**
 * Qui di seguito si fornisce una implementazione che usa gli Stream
 * Implementazioni analoghe senza gli Stream sono comunque simili
 */
public class BagFactoryImpl implements BagFactory {
    
    // Nota: uso dei SuppresWarnings non necessario all'esame
    // Tuttavia, quella presentata è la giusta (e avanzata) soluzione per evitare di creare
    // nuove Bag vuote ogni volta
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final Bag EMPTY = new BagImpl(Collections.emptyMap()); 
    
    //package protected.. accessible to FactoryImpl
    private static <X> void addMany(Map<X,Integer> map, X x, int copies){
        map.merge(x, copies, (k,v) -> v+copies);
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public <X> Bag<X> empty() {
        return (Bag<X>)EMPTY;
    }
    
    private <X> Bag<X> fromIteratorWithCopies(Iterator<X> iterator, ToIntFunction<X> copies) {
        final Map<X,Integer> map = new HashMap<>();
        iterator.forEachRemaining(x -> addMany(map,x,copies.applyAsInt(x)));
        return new BagImpl<>(map);
    }

    private <X> Bag<X> fromIterator(Iterator<X> iterator) {
        return fromIteratorWithCopies(iterator, x->1);
    }
    
    @Override
    public <X> Bag<X> fromSet(Set<X> set) {
        return this.fromIterator(set.iterator());
    }

    @Override
    public <X> Bag<X> fromList(List<X> list) {
        return this.fromIterator(list.iterator());    
    }
    
    @Override
    public <X> Bag<X> bySupplier(Supplier<X> supplier, int elements, ToIntFunction<X> copies) {
        return fromIteratorWithCopies(Stream.generate(supplier).limit(elements).iterator(),copies);
    }

    @Override
    public <X> Bag<X> byIteration(X first, UnaryOperator<X> next, int elements, ToIntFunction<X> copies) {
        return fromIteratorWithCopies(Stream.iterate(first,next).limit(elements).iterator(),copies);
    }

}
