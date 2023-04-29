package a02a.sol1;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    private <X> RecursiveIterator<X> cons(X element, Supplier<RecursiveIterator<X>> next){
        return new RecursiveIterator<X>() {

            @Override
            public X getElement() {
                return element;
            }

            @Override
            public RecursiveIterator<X> next() {
                return next.get();
            }
        };
    }

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return fromIterator(list.iterator());
    }

    private <X> RecursiveIterator<X> fromIterator(Iterator<X> iterator) {
        if (iterator.hasNext()) {
            return cons(iterator.next(), ()->fromIterator(iterator));
        }
        return null;
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> iterator, int max) {
        return toStream(iterator,max).collect(Collectors.toList());
    }

    private <X> Stream<X> toStream(RecursiveIterator<X> iterator, int max) {
        if (max == 0 || iterator == null){
            return Stream.empty();
        }
        return Stream.concat(Stream.of(iterator.getElement()), toStream(iterator.next(), max-1));
    }

    
    private <X> RecursiveIterator<X> iterate(X first, UnaryOperator<X> next) {
        return cons(first, ()->iterate(next.apply(first), next));
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        if (first != null && second != null){
            return cons(new Pair<>(first.getElement(),second.getElement()), ()->zip(first.next(),second.next()));
        } 
        return null;
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return zip(iterator,iterate(0,x->x+1));
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        if (first != null) {
            return cons(first.getElement(), ()->alternate(second,first.next()));
        }
        return second;
    }
}
