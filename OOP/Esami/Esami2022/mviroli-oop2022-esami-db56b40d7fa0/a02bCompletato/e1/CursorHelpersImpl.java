package a02b.e1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CursorHelpersImpl implements CursorHelpers{

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return new Cursor<X>() {
            Iterator<X> it = list.iterator();
            private X elem = it.next();

            @Override
            public X getElement() {
                return elem;
            }

            @Override
            public boolean advance() {
                if(it.hasNext()) {
                    elem = it.next();
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public Cursor<Integer> naturals() {
        return new Cursor<Integer>() {
            private int count = 0;

            @Override
            public Integer getElement() {
                return count;
            }

            @Override
            public boolean advance() {
                count++;
                return true;
            }
            
        };
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<X>() {
            private int count = 0;

            @Override
            public X getElement() {
                this.count++;
                return input.getElement();
            }

            @Override
            public boolean advance() {
                if (max > count && input.advance()) {
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        do {
            consumer.accept(input.getElement());
        } while(input.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        final List<X> res = new ArrayList<>();
        int count = 0;
        do {
            res.add(input.getElement());
            count++;
        } while(input.advance() && count < max);
        
        return res;
    }
    
}