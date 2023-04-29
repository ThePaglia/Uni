package a01b.sol1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    private <I,O> Flattener<I,O> generic(BiFunction<List<O>, List<I>, List<O>> folder, Predicate<List<O>> toEmit){
        return new Flattener<I,O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                final List<O> outList = new ArrayList<>();
                List<O> state = new ArrayList<>();
                for (var l: list){
                    if (toEmit.test(state)){
                        state = folder.apply(state,l);
                        outList.addAll(state);
                        state = new ArrayList<>();
                    } else {
                        state = folder.apply(state,l);
                    }
                }
                outList.addAll(state);
                return outList;
            }
            
        };
    }

    private <X> List<X> append(List<X> list1, List<X> list2){
        return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
    }

    private List<Integer> sumTwoVectors(List<Integer> list1, List<Integer> list2){
        return list1.isEmpty() ? list2 : IntStream.range(0, list1.size()).mapToObj(i -> list1.get(i) + list2.get(i)).collect(Collectors.toList());
    }
    

    @Override
    public <X, Y> Flattener<X, Y> each(Function<List<X>, Y> mapper) {
        return generic((s,l) -> List.of(mapper.apply(l)), s -> true);
    }


    @Override
    public Flattener<Integer,Integer> sumEach() {
        return each(l -> l.stream().mapToInt(i->i).sum());
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return generic(this::append, s -> false);
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return generic((s,l) -> List.of(append(s,l).stream().reduce("",String::concat)), s -> !s.isEmpty());
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return generic(this::sumTwoVectors, s -> false);
    }

}
