package a03b.e1;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class LensFactoryImpl implements LensFactory{

    private <I, O> Lens<I, O> generic(Function<I,O> topG, BiFunction<O, I, I> topS){
        return new Lens<I,O>() {

            @Override
            public O get(I s) {
                return topG.apply(s);
            }

            @Override
            public I set(O a, I s) {
                return topS.apply(a, s);
            }
            
        };
    }

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return generic(list -> list.get(i), 
                      (val, list) -> IntStream.range(0, list.size())
                                    .mapToObj(j -> j != i ? list.get(j) : val)
                                    .toList());
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return generic(list -> list.get(i).get(j), 
                      (val, list) -> IntStream.range(0, list.size())
                                    .mapToObj(riga -> riga != i ? list.get(riga) 
                                                                : IntStream.range(0, list.get(riga).size())
                                                                  .mapToObj(colonna -> colonna != j ? list.get(riga).get(colonna) 
                                                                                                    : val).toList()).toList());
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return generic(pair -> pair.get1(), (val, pair) -> new Pair<>(val, pair.get2()));
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return generic(pair -> pair.get2(), (val, pair)-> new Pair<>(pair.get1(), val));
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        return generic(list -> list.get(i).get2().get2(),
                      (val, list) -> IntStream.range(0, list.size())
                                    .mapToObj(riga -> riga != i ? list.get(riga)
                                                                : new Pair<>(list.get(riga).get1(), new Pair<>(list.get(riga).get2().get1(), val)))
                                    .toList());
    }

}
