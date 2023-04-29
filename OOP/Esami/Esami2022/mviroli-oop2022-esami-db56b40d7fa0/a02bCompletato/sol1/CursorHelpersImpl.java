package a02b.sol1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> fromNonEmptyIterator(Iterator<X> iterator) {
        return new Cursor<X>() {

            X element = iterator.next();

            @Override
            public X getElement() {
                return element;
            }

            @Override
            public boolean advance() {
                if (iterator.hasNext()){
                    element = iterator.next();
                    return true;
                }
                return false;
            }
        };
    }
    
    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return this.fromNonEmptyIterator(list.iterator());
    }

    @Override
    public Cursor<Integer> naturals() {
        return this.fromNonEmptyIterator(Stream.iterate(0, x->x+1).iterator());
    }


    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<>(){

            int count = 0;

            @Override
            public X getElement() {
                return input.getElement();
            }

            @Override
            public boolean advance() {
                if (count < max-1){
                    count++;
                    return input.advance();
                }
                return false;
            }
        };
    }

    @Override
    public <X> void forEach(Cursor<X> cursor, Consumer<X> consumer) {
        do {
            consumer.accept(cursor.getElement());
        } while (cursor.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> cursor, int max) {
        var list = new ArrayList<X>();
        this.forEach(take(cursor, max), list::add);
        return list;
    }
}
