package a04.sol1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import static a04.sol1.FunctionalStreamTemplate.NextResultImpl;

public class FunctionalStreamFactoryImpl implements FunctionalStreamFactory {
	
	@Override
	public <E> FunctionalStream<E> iterate(E initial, UnaryOperator<E> op) {
		return (FunctionalStreamTemplate<E>)
				() -> new NextResultImpl<>(initial, iterate(op.apply(initial),op));
	}
	
	@Override
	public <A, B> FunctionalStream<B> map(FunctionalStream<A> fstream, Function<A, B> mapper) {
		return (FunctionalStreamTemplate<B>)
				() -> new NextResultImpl<>(mapper.apply(fstream.next().getElement()),map(fstream.next().getStream(),mapper));

	}

	@Override
	public <E> FunctionalStream<E> fromListRepetitive(List<E> list) {
		return map(map(iterate(0,x->x+1), x -> x % list.size()), list::get);
	}
}
