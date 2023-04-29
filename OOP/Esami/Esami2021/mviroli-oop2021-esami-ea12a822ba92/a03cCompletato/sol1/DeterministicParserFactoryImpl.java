package a03c.sol1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DeterministicParserFactoryImpl implements DeterministicParserFactory {
	
	private static <E> Optional<List<E>> takeTail(List<E> list){
		if (list.isEmpty()) {
			return Optional.empty();
		}
		var l = new LinkedList<>(list);
		l.removeFirst();
		return Optional.of(l);
	}

	@Override
	public DeterministicParser oneSymbol(String s) {
		return filteredSymbol(str -> str.equals(s));
	}

	@Override
	public DeterministicParser twoSymbols(String s1, String s2) {
		return sequence(oneSymbol(s1), oneSymbol(s2));
	}

	@Override
	public DeterministicParser sequence(DeterministicParser first, DeterministicParser second) {
		return tokens -> first.accepts(tokens).flatMap(second::accepts);
	}

	private DeterministicParser fullSequence(List<DeterministicParser> list) {
		return list.size() == 1 ? list.get(0) : sequence(list.get(0),fullSequence(takeTail(list).get()));
	}

	@Override
	public DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers() {
		return increasingSequenceOfNumbers(0); 
	}
	
	private DeterministicParser increasingSequenceOfNumbers(int lowerBound){
		return tokens -> {
			var res1 = filteredSymbol(str -> Integer.parseInt(str)>lowerBound).accepts(tokens);
			return res1.flatMap(t -> increasingSequenceOfNumbers(Integer.parseInt(tokens.get(0))).accepts(t)).or(()->Optional.of(tokens));
		};
	}
	
	private DeterministicParser filteredSymbol(Predicate<String> predicate) {
		return tokens ->
		takeTail(tokens).filter(t-> predicate.test(tokens.get(0)));
	}
	
	private DeterministicParser many(DeterministicParser parser) {
		return tokens ->
			parser.accepts(tokens).isEmpty() ? Optional.of(tokens) : this.sequence(parser, many(parser)).accepts(tokens);
	}

	@Override
	public DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter,
			DeterministicParser element) {
		return this.fullSequence(List.of(
				oneSymbol(start), element, many(sequence(oneSymbol(delimiter),element)), oneSymbol(stop)
				));
	}

}
