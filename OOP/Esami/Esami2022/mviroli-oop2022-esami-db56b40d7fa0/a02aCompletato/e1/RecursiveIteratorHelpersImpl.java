package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers{

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        if (list.isEmpty()) {
            return null;
        }
        return new RecursiveIterator<X>() {
            Iterator<X> it = list.iterator();
            X next = it.next();

            @Override
            public X getElement() {
                return next;
            }

            @Override
            public RecursiveIterator<X> next() {
                List<X> rest = new ArrayList<>();
                while(it.hasNext()) {
                    rest.add(it.next());
                }
                return fromList(rest);
            }
        };
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> res = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            if (input != null) {
                res.add(input.getElement());
                input = input.next();
            }
        }
        return res;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return new RecursiveIterator<Pair<X,Y>>() {
            
            private RecursiveIterator<X> f = first;
            private RecursiveIterator<Y> s = second;

            @Override
            public Pair<X, Y> getElement() {
                return new Pair<>(f.getElement(), s.getElement());
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {
                f = f.next();
                s = s.next();
                if (f == null || s == null) {
                    return null;
                }
                return zip(f, s);
            }
            
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return new RecursiveIterator<Pair<X,Integer>>() {
            private int count = 0;
            private RecursiveIterator<X> it = iterator;


            @Override
            public Pair<X, Integer> getElement() {
                return new Pair<X,Integer>(it.getElement(), count);
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                it = it.next();
                count++;
                if (it == null) {
                    return null;
                }
                return this;
            }
            
        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        return new RecursiveIterator<X>() {

            private RecursiveIterator<X> f = first;
            private RecursiveIterator<X> s = second;
            private int turn = 0;

            @Override
            public X getElement() {
                if (turn % 2 == 0) {
                    return f.getElement();
                } else {
                    return s.getElement();
                }
            }
            
            @Override
            public RecursiveIterator<X> next() {
                if (turn % 2 == 0) {
                    f = f.next();
                } else {
                    s = s.next();
                }
                turn++;
                if (f == null) {
                    turn = 1;
                }
                if (s == null) {
                    turn = 0;
                }
                if (f == null && s == null) {
                    return null;
                } else {
                    return this;
                }
            }
            
        };
    }
    
}