package a04.sol1alternative;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EitherFactoryImpl implements EitherFactory {

	@Override
	public <A, B> Either<A, B> success(B b) {
		return new Either<>(){

			@Override
			public boolean isFailure() {
				return false;
			}

			@Override
			public boolean isSuccess() {
				return true;
			}

			@Override
			public Optional<A> getFailure() {
				return Optional.empty();
			}

			@Override
			public Optional<B> getSuccess() {
				return Optional.of(b);
			}

			@Override
			public B orElse(B other) {
				return b;
			}

			@Override
			public <B1> Either<A, B1> map(Function<B, B1> function) {
				return success(function.apply(b));
			}

			@Override
			public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
				return function.apply(b);
			}

			@Override
			public <A1> Either<A1, B> filterOrElse(Predicate<B> predicate, A1 failure) {
				return predicate.test(b) ? success(b) : failure(failure);
			}

			@Override
			public <C> C fold(Function<A, C> funFailure, Function<B, C> funSuccess) {
				return funSuccess.apply(b);
			}
			
		};
	}

	@Override
	public <A, B> Either<A, B> failure(A a) {
		return new Either<>(){

			@Override
			public boolean isFailure() {
				return true;
			}

			@Override
			public boolean isSuccess() {
				return false;
			}

			@Override
			public Optional<A> getFailure() {
				return Optional.of(a);
			}

			@Override
			public Optional<B> getSuccess() {
				return Optional.empty();
			}

			@Override
			public B orElse(B other) {
				return other;
			}

			@Override
			public <B1> Either<A, B1> map(Function<B, B1> function) {
				return failure(a);
			}

			@Override
			public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
				return failure(a);
			}

			@Override
			public <A1> Either<A1, B> filterOrElse(Predicate<B> predicate, A1 failure) {
				return failure(failure);
			}

			@Override
			public <C> C fold(Function<A, C> funFailure, Function<B, C> funSuccess) {
				return funFailure.apply(a);
			}
			
		};
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
