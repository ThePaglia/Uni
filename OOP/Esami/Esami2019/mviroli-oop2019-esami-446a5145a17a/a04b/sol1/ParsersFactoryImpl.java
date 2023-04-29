package a04b.sol1;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ParsersFactoryImpl implements ParsersFactory {
	
	private abstract class AbstractParser<R> implements Parser<R> {
		
		protected boolean correct = true;
		protected R result; 
		
		protected AbstractParser(R initialResult) {
			this.result = initialResult;
		}
		
		@Override
		public boolean getNext(String token) { // template method
			this.correct = this.correct && canBeAccepted(token);
			this.updateResult(token);
			return this.correct;
		}
		
		protected abstract boolean canBeAccepted(String token);
		
		protected abstract void updateResult(String token);
		
		protected abstract boolean sentenceIsComplete();

		@Override
		public R completeAndCreateResult() { // template method
			if (!this.correct || !this.sentenceIsComplete()) {
				throw new IllegalStateException();
			}
			return this.result;
		}
		
		@Override
		public boolean getAllInList(List<String> tokens) { // template method
			return tokens.stream().allMatch(this::getNext);
		}
	}

	@Override
	public Parser<Integer> createSequenceParserToCount(String t) {
		return new AbstractParser<Integer>(0) {

			@Override
			protected boolean canBeAccepted(String token) {
				return token.equals(t);
			}

			@Override
			protected void updateResult(String token) {
				this.result++;
			}

			@Override
			protected boolean sentenceIsComplete() {
				return true;
			}
		
		};
	}

	@Override
	public Parser<String> createNonEmptySequenceParserToString(String t) {
		return new AbstractParser<String>("") {

			@Override
			protected boolean canBeAccepted(String token) {
				return token.equals(t);
			}

			@Override
			protected void updateResult(String token) {
				this.result = this.result + token;
			}

			@Override
			protected boolean sentenceIsComplete() {
				return this.result.length()>0;
			}
		
		};
	}

	@Override
	public Parser<Integer> createExpressionParserToResult() {
		return new AbstractParser<Integer>(0) {
			private final Set<String> OPERATORS = Set.of("+","-");
			private final Set<String> NUMBERS = Set.of("0","1");
			private Optional<String> previousToken = Optional.empty();
			
			private int num(String t) {
				return t.equals("0") ? 0 : 1;
			}
			
			private java.util.function.BinaryOperator<Integer> op(String t) {
				return t.equals("+") ? (a,b)->a+b : (a,b)->a-b;
			}

			@Override
			protected boolean canBeAccepted(String token) {
				if (previousToken.isEmpty() || OPERATORS.contains(previousToken.get())) {
					return NUMBERS.contains(token);
				} else {
					return OPERATORS.contains(token);
				}
			}

			@Override
			protected void updateResult(String token) {
				if (previousToken.isEmpty()) {
					this.result = num(token);
				} else if ( OPERATORS.contains(previousToken.get()) ) {
					this.result = op(previousToken.get()).apply(this.result, num(token));
				} 
				this.previousToken = Optional.of(token);
				System.out.println("Aft "+result + " " + token + " " + previousToken);
			}

			@Override
			protected boolean sentenceIsComplete() {
				return previousToken.isPresent() && !OPERATORS.contains(previousToken.get());
			}
		
		};
	}

	
}
