package a03a.sol1;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParserFactoryImpl implements ParserFactory {

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X,X>> transitions, Set<X> acceptanceInputs){
        return fromGraph(Set.of(x0), transitions, acceptanceInputs);
    }

    private <X> Parser<X> fromGraph(Set<X> initial, Set<Pair<X,X>> transitions, Set<X> acceptanceInputs){
        return recursive(
            x -> initial.contains(x) 
                 ? Optional.of(fromGraph(transitions.stream().filter(p -> p.getX().equals(x)).map(Pair::getY).collect(Collectors.toSet()), transitions, acceptanceInputs)) 
                 : Optional.empty(),
                    initial.isEmpty());
    }


    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return recursive(x -> Optional.of(fromFinitePossibilities(acceptedSequences
            .stream()
            .filter(l -> !l.isEmpty())
            .filter(l -> l.get(0).equals(x))
            .map(l -> new LinkedList<>(l.subList(1, l.size())))
            .collect(Collectors.toSet()))), acceptedSequences.contains(Collections.emptyList()));
    }

    private <X> Parser<X> empty() {
        return this.fromFinitePossibilities(Set.of(Collections.emptyList()));
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        return recursive(x -> x.equals(x0) 
            ? Optional.of(next.apply(x0).map(y -> fromIteration(y,next)).orElse(empty()))
            : Optional.empty(),
            false
        );
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        return new Parser<>(){

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (!iterator.hasNext()){
                    return isFinal;
                }
                return nextParser.apply(iterator.next()).map(p -> p.accept(iterator)).orElse(false);
            }
        };
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x0, Parser<X> parser) {
        return recursive(x -> x.equals(x0) 
            ? Optional.of(parser)
            : Optional.empty(),
            false
        );
    }

    public <X> Parser<X> fromParserWithSkip(Parser<X> parser, int i) {
        return new Parser<>(){

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (i == 0 || !iterator.hasNext()){
                    return parser.accept(iterator);
                } 
                iterator.next();
                return fromParserWithSkip(parser, i-1).accept(iterator);
            }
            
        };
    }


}
