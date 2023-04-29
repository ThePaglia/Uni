package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ScannerFactoryImpl implements ScannerFactory {

    @Override
    public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {
        return new Scanner<X,List<Y>>() {
            private List<X> l = new ArrayList<>();
            @Override
            public List<Y> scan(Iterator<X> input) {
                while (input.hasNext()) {
                    l.add(input.next());
                }

                return l.stream().filter(filter).map(mapper).collect(Collectors.toList());
            }
            
        };
    }

    @Override
    public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
        return new Scanner<X,Optional<X>>() {
            private List<X> l = new ArrayList<>();

            @Override
            public Optional<X> scan(Iterator<X> input) {
                while (input.hasNext()) {
                    X next = input.next();
                    if (filter.test(next)) {
                        return Optional.of(next);
                    }
                    l.add(next);
                }
                
                return l.stream().filter(filter).findFirst();
            }
        };
    }

    @Override
    public Scanner<Integer, Optional<Integer>> maximalPrefix() {
        return new Scanner<Integer,Optional<Integer>>() {
    
            @Override
            public Optional<Integer> scan(Iterator<Integer> input) {
                if (!input.hasNext()) {
                    return Optional.empty();
                }
    
                int prefix = input.next();
                while (input.hasNext()) {
                    int next = input.next();
                    if (next >= prefix) {
                        prefix = next;
                    } else {
                        break;
                    }
                }
    
                return Optional.of(prefix);
            }
        };
    }
    

    @Override
    public Scanner<Integer, List<Integer>> cumulativeSum() {
        return new Scanner<Integer,List<Integer>>() {
            private List<Integer> cumulativeSums = new ArrayList<>();
            private int sum = 0;
    
            @Override
            public List<Integer> scan(Iterator<Integer> input) {
                if (input.hasNext()) {
                    sum += input.next();
                    cumulativeSums.add(sum);
                }
                while (input.hasNext()) {
                    sum += input.next();
                    cumulativeSums.add(sum);
                }
                return cumulativeSums;
            }
        };
    }
    
}
