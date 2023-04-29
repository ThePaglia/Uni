package a03c.e1;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DeterministicParserFactoryImpl implements DeterministicParserFactory {

    private DeterministicParser generic(Function<List<String>, Optional<List<String>>> func) {
        return new DeterministicParser() {

            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                return func.apply(tokens);
            }
            
        };
    }

    @Override
    public DeterministicParser oneSymbol(String s) {
        return generic(tokens -> tokens.contains(s) ? Optional.of(tokens.stream().filter(i -> i != s).toList()) : Optional.empty());
    }

    @Override
    public DeterministicParser twoSymbols(String s1, String s2) {
        return generic(tokens -> tokens.contains(s1) && tokens.contains(s2) ? Optional.of(tokens.stream().filter(i -> i != s1 && i != s2).toList()) : Optional.empty());
    }

    @Override
    public DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers() {
        // TODO Auto-generated method stub
        return generic(tokens -> Integer.parseInt(tokens.get(0)) < 0 ? Optional.of(tokens)
                                : Optional.of(IntStream.range(1, tokens.size())
                                                .dropWhile(index -> Integer.parseInt(tokens.get(index)) > Integer.parseInt(tokens.get(index - 1)))
                                                .mapToObj(x -> tokens.get(x)).toList()));
    }

    @Override
    public DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter,
            DeterministicParser element) {
        // TODO Auto-generated method stub
        return generic(tokens -> tokens.get(0) != start ? Optional.empty()
                        : IntStream.range(1, tokens.size())
                            .filter(i -> i%2 == 1)
                            .dropWhile(index -> ));
    }

    @Override
    public DeterministicParser sequence(DeterministicParser first, DeterministicParser second) {
        // TODO Auto-generated method stub
        return generic(tokens -> second.accepts(first.accepts(tokens).get()));
    }

}
