package a06.sol1;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This is a rather advanced solution, which some students may study and enjoy.
 * A simpler solution would be to develop each parser as a variation of the implementation of method oneOf.
 */
public class ParserFactoryImpl implements ParserFactory {

	@Override
	public Parser one(String string) {
		// 'one' is clearly a particular case of 'oneOf'
		return this.oneOf(Collections.singleton(string));
	}
	
	@Override
	public Parser oneOf(Set<String> list) {
		return new Parser() {
			
			private boolean consumed = false; // just need a flag as state!

			@Override
			public boolean acceptToken(String token) {
				if (this.inputCompleted()) {
					return false;
				}
				consumed = true;
				return list.contains(token);
			}

			@Override
			public boolean inputCompleted() {
				return consumed;
			}

			@Override
			public void reset() {
				consumed = false;
			}
			
		};
	}
	
	@Override
	public Parser many(String token, int count) {
		return many(this.one(token),count); // use a more general parser, see below
	}
	
	/**
	 * @return a parser that performs parsing of 'p' for 'count' times
	 */
	private Parser many(Parser p, int count) {
		return new Parser() {
			int c = count;
			@Override
			public boolean acceptToken(String token) {
				if (p.inputCompleted() && c>0) {
					p.reset();
					c--;
				}
				return c>0 && p.acceptToken(token);
			}

			@Override
			public boolean inputCompleted() {
				return c==0 || (c==1 && p.inputCompleted());
			}
			
			@Override
			public void reset() {
				c = count;
				p.reset();
			}
		};
	}
	
	@Override
	public Parser sequence(String token1, String token2) {
		return this.sequence(one(token1),one(token2)); // use a more general parser, see below
	}
	
	/**
	 * @return a parser that performs parsing of 'p1' and then of 'p2'
	 */
	private Parser sequence(Parser p1, Parser p2) {
		return new Parser() {		
			@Override
			public boolean acceptToken(String string) {
				return (p1.inputCompleted() ? p2 : p1).acceptToken(string);
			}
			@Override
			public boolean inputCompleted() {
				return (p1.inputCompleted() && p2.inputCompleted());
			}
			@Override
			public void reset() {
				p1.reset();
				p2.reset();
			}						
		};
		
	}
	
	/**
	 * generalise the above parser with a sequence of n parsers
	 */
	private Parser sequence(Parser p, Parser... parsers) {
		Parser out = p;
		for (Parser p2: parsers) {
			out = sequence(out,p2);
		}
		return out;
	}
	

	/* 
	 * And here's the magic! Obtain this parser by combining the above ones!
	 * This technique is called "combinators" (a functional pattern), enjoy it!
	 * Of course, the student could also implement fullSequence with an ad-hoc parser, as we did for 'oneOf'
	 */
	@Override
	public Parser fullSequence(String begin, Set<String> elem, String separator, String end, int count) {
		return sequence(one(begin),
				        oneOf(elem),
				        many(sequence(one(separator),oneOf(elem)),count-1),
				        one(end));
	}
}
