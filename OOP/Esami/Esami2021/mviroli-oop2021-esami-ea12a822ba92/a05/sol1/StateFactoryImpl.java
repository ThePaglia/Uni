package a05.sol1;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class StateFactoryImpl implements StateFactory {

	@Override
	public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
		return new State<S,A>(){

			@Override
			public S nextState(S s) {
				return fun.apply(s).get1();
			}

			@Override
			public A value(S s) {
				return fun.apply(s).get2();
			}
			
			@Override
			public <B> State<S, B> map(Function<A, B> mapper) {
				return fromFunction(s -> new Pair<>(nextState(s), mapper.apply(value(s))));
			}

			@Override
			public Iterator<A> iterator(S s0) {
				return Stream.iterate(next(this,s0), p -> next(this,p.get1())).map(p -> p.get2()).iterator();
			}
		};
	}
	
	private <S2,A2> Pair<S2,A2> next(State<S2,A2> state, S2 s) {
		return new Pair<>(state.nextState(s), state.value(s));
	}
	
	@Override
	public <S,A,B> State<S,B> compose(State<S,A> state1, State<S,B> state2) {
		return fromFunction(s -> {
			var p1 = next(state1, s);
			var p2 = next(state2, p1.get1());
			return new Pair<>(p2.get1(), p2.get2());
		});
	}

	@Override
	public State<Integer, String> incSquareHalve() {
		var s = this.<Integer,Integer>fromFunction(i -> new Pair<>(i+1,i+1));
		s = compose(s, this.<Integer,Integer>fromFunction(i -> new Pair<>(i*i,i*i)));
		s = compose(s, this.<Integer,Integer>fromFunction(i -> new Pair<>(i/2,i/2)));
		return s.map(String::valueOf);
	}
	
	@Override
	public State<Integer, Integer> counterOp(CounterOp op) {
		return this.fromFunction(
				op == CounterOp.INC ? i -> new Pair<>(i+1,null) :
			    op == CounterOp.RESET ? i -> new Pair<>(0, null) :
			    op == CounterOp.GET ? i -> new Pair<>(i,i) : 
			    null);	
	}
}
