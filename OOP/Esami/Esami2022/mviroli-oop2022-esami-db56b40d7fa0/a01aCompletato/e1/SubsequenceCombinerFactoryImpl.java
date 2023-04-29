package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    private <I, O> SubsequenceCombiner<I, O> generic(Function<List<I>, List<O>> function) {
        return new SubsequenceCombiner<I,O>() {

            @Override
            public List<O> combine(List<I> list) {
                return function.apply(list);
            }
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return generic(l -> IntStream.range(0, l.size())
                            .filter(index -> index%3==0)
                            .mapToObj(i -> i+3 < l.size() ? l.subList(i, i+3).stream().mapToInt(Integer::intValue).sum()
                                                        : l.subList(i, l.size()).stream().mapToInt(Integer::intValue).sum())
                            .toList());
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return generic(l -> IntStream.range(0, l.size())
                            .filter(index -> index%3==0)
                            .mapToObj(i -> i+3 < l.size() ? l.subList(i, i+3)
                                                        : l.subList(i, l.size()))
                            .toList());
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return generic(l -> new ArrayList<>() {{
            int count = 0;
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i) == 0) {
                    add(count);
                    count = 0;
                } else {
                    count++;
                    if (i == l.size()-1) {
                        add(count);
                    }
                }
            }
        }});
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return generic(l -> l.stream().map(function).toList());
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return null;
    }

}
