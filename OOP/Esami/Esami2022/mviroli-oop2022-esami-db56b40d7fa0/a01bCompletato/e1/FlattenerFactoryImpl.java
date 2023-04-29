package a01b.e1;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    public <I, O> Flattener <I, O> generic(Function<List<List<I>>, List<O>> function) {
        return new Flattener<I,O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                return function.apply(list);
            }
            
        };
    }

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return generic(list -> list.stream().map(inList -> inList.stream().mapToInt(i -> i).sum()).toList());
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return generic(list -> list.stream().flatMap(inList -> inList.stream()).toList());
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return generic(list -> IntStream.range(0, list.size())
                                        .filter(index -> index % 2 == 0)
                                        .mapToObj(i -> i+1 < list.size() ? list.get(i).stream().collect(Collectors.joining()).concat(list.get(i+1).stream().collect(Collectors.joining()))
                                                                    : list.get(i).stream().collect(Collectors.joining()))
                                                                    .toList());
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return generic(list -> list.stream().map(inList -> mapper.apply(inList)).toList());
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return generic(list -> IntStream.range(0, list.get(0).size())
                                        .mapToObj(i -> list.stream().mapToInt(inList -> inList.get(i)).sum()).toList());
    }

}
