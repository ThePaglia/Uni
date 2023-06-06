package a03a.sol1;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * An interface modelling a factory of Parser. As in most factories,
 * the call of a method leaves no side-effect.
 */
public interface ParserFactory {
    
    /**
     * @param <X>
     * @param acceptedSequences
     * @return a parser that accept a sequence only if it is one of the input set
     * e.g. with input { [1,2,3], [1,5,6], [7,8]}, the parser accepts sequence (1,2,3)
     * but does not accept sequence (1,5,7)
     */
    <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences);

    /**
     * @param <X>
     * @param x0
     * @param transitions
     * @param acceptanceInputs
     * @return a parser that starts accepting x0, then recursively proceeds accepting
     * x1 if pair (x0,x1) is in transitions set, and so on. A sequence is correctly
     * parsed if recursion/iteration correctly reaches a final xn that is contained in
     * acceptanceInputs
     */
    <X> Parser<X> fromGraph(X x0, Set<Pair<X,X>> transitions, Set<X> acceptanceInputs);

    /**
     * @param <X>
     * @param x0
     * @param next
     * @return a parser that starts accepting x0, then recursively proceeds accepting
     * x1 if next(x0) gives x1, and so on. A sequence is correctly
     * parsed when the next element is the empty optional
     */
    <X> Parser<X> fromIteration(X x0, Function<X,Optional<X>> next);

    /**
     * @param <X>
     * @param nextParser
     * @param isFinal
     * @return a parser that starts accepting a certain x, then recursively proceeds likewise
     * the parser nextParser(x), and so on. If nextParser is the empty Optional, it means
     * the x element is NOT accepted. The empty sequence is correctly
     * parsed if isFinal is true.
     */
    <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal);

    /**
     * @param <X>
     * @param x
     * @param parser
     * @return a parser that first accepts x, and then proceeds likewise parser
     */
    <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser);   
}
