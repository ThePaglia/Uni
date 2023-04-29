package a03b.sol1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LensFactoryImpl implements LensFactory {

	private static <E> List<E> copyAndSet(List<E> l, int index, E e){
		var lo = new ArrayList<>(l);
		lo.set(index, e);
		return lo;		
	}
	
	@Override
	public <E> Lens<List<E>, E> indexer(int i) {
		return general(s -> s.get(i), (a,s) -> copyAndSet(s,i,a));
	}

	@Override
	public <A, B> Lens<Pair<A, B>, A> left() {
		return general(Pair::get1, (a,s) -> new Pair<>(a, s.get2()));
	}

	@Override
	public <A, B> Lens<Pair<A, B>, B> right() {
		return general(Pair::get2, (b,s) -> new Pair<>(s.get1(), b));
	}

	@Override
	public <A,B,C> Lens<List<Pair<A,Pair<B,C>>>,C> rightRightAtPos(int i){
		return compose(indexer(i),compose(right(),right()));
	}
	
	@Override
	public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
		return compose(indexer(i),indexer(j));
	}
	
	private <S, A, B> Lens<S, B> compose(Lens<S, A> l1, Lens<A, B> l2) {
		return general(s -> l2.get(l1.get(s)), (b,s) -> l1.set(l2.set(b, l1.get(s)),s));
	}


	private <S, A> Lens<S, A> general(Function<S, A> getter, BiFunction<A, S, S> setter) {
		return new Lens<>() {

			@Override
			public A get(S s) {
				return getter.apply(s);
			}

			@Override
			public S set(A a, S s) {
				return setter.apply(a, s);
			}
			
		};
	}
}
