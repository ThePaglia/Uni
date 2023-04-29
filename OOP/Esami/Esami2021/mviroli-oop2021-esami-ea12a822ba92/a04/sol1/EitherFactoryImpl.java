package a04.sol1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Supplier;

public class EitherFactoryImpl implements EitherFactory {

	@Override
	public <A, B> Either<A, B> success(B b) {
		return EitherImpl.createSuccess(b);
	}

	@Override
	public <A, B> Either<A, B> failure(A a) {
		return EitherImpl.createFailure(a);
	}

	@Override
	public <A> Either<Exception, A> of(Supplier<A> computation) {
		try {
			return success(computation.get());
		} catch (Exception e) {
			return failure(e);
		}
	}
	
	@Override
	public <A, B, C> Either<A, List<C>> traverse(List<B> list, Function<B, Either<A, C>> function) {
		return list
				.stream()
				.map(function::apply)
				.map(e -> e.map(Stream::of))
				.reduce(
						success(Stream.of()),
						(e1, e2) -> e1.flatMap(l1 -> e2.map(l2 -> Stream.concat(l1,l2)))) // map2(e1,e2,Stream::concat)
				.map(s -> s.collect(Collectors.toList()));
	}
	
	public <A, B, C> Either<A, List<C>> alternativeTraverse(List<B> list, Function<B, Either<A, C>> function) {
		Either<A, List<C>> e = success(new ArrayList<>());
		for (var b: list) {
			var e2 = function.apply(b);
			if (e2.isFailure()) {
				return failure(e2.getFailure().get());
			}
			e.getSuccess().get().add(e2.getSuccess().get());
		}
		return e;
	}

}
