package a04.sol1;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class EitherImpl<A,B> implements Either<A, B> {
	
	private final Optional<A> failurePart;
	private final Optional<B> successPart;
	
	static <A,B> Either<A,B> createFailure(A a){
		return new EitherImpl<>(Optional.of(a), Optional.empty());
	}
	
	static <A,B> Either<A,B> createSuccess(B b){
		return new EitherImpl<>(Optional.empty(), Optional.of(b));
	}
	
	private EitherImpl(Optional<A> failurePart, Optional<B> successPart) {
		if (failurePart.isEmpty() ^ successPart.isPresent()) {
			throw new IllegalArgumentException();
		}
		this.failurePart = failurePart;
		this.successPart = successPart;
	}

	@Override
	public boolean isFailure() {
		return this.failurePart.isPresent();
	}

	@Override
	public boolean isSuccess() {
		return !this.isFailure();
	}

	@Override
	public Optional<A> getFailure() {
		return this.failurePart;
	}
	
	@Override
	public Optional<B> getSuccess() {
		return this.successPart;
	}

	@Override
	public B orElse(B other) {
		return this.successPart.orElse(other);
	}

	@Override
	public <B1> Either<A, B1> map(Function<B, B1> function) {
		return fold(EitherImpl::createFailure, function.andThen(EitherImpl::createSuccess)); // r -> ofRight(function.apply(r)));
	}

	@Override
	public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
		return fold(EitherImpl::createFailure, function::apply);
	}

	@Override
	public <A1> Either<A1,B> filterOrElse(Predicate<B> predicate, A1 failure) {
		return this.isFailure() || !predicate.test(this.successPart.get()) ? createFailure(failure) : createSuccess(successPart.get()); 
	}

	@Override
	public <C> C fold(Function<A,C> funFailure, Function<B,C> funSuccess) {
		return this.isFailure() ? funFailure.apply(failurePart.get()) : funSuccess.apply(successPart.get());
	}
}
