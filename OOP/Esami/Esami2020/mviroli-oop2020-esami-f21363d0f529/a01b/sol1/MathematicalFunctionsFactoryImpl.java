package a01b.sol1;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class MathematicalFunctionsFactoryImpl implements MathematicalFunctionsFactory {

	@Override
	public <A, B> MathematicalFunction<A, B> constant(Predicate<A> domainPredicate, B value) {
		return fromFunction(domainPredicate,a->value);
	}

	@Override
	public <A, B> MathematicalFunction<A, A> identity(Predicate<A> domainPredicate) {
		return fromFunction(domainPredicate,a->a);
	}
	
	@Override
	public <A, B> MathematicalFunction<A, B> fromMap(Map<A, B> map) {
		return fromFunction(map::containsKey, map::get);
	}

	@Override
	public <A, B> MathematicalFunction<A, B> fromFunction(Predicate<A> domainPredicate, Function<A, B> function) {
		return new MathematicalFunction<A,B>(){

			@Override
			public Optional<B> apply(A a) {
				return Optional.of(a).filter(this::inDomain).map(function::apply);
			}

			@Override
			public boolean inDomain(A a) {
				return domainPredicate.test(a);
			}

			@Override
			public <C> MathematicalFunction<A, C> composeWith(MathematicalFunction<B, C> function2) {
				return fromFunction(domainPredicate, a -> function2.apply(function.apply(a)).get());
			}

			@Override
			public MathematicalFunction<A, B> restrict(Set<A> subDomain) {
				return fromFunction(a -> domainPredicate.test(a) && subDomain.contains(a), function);
			}

			@Override
			public MathematicalFunction<A, B> withUpdatedValue(A domainValue, B codomainValue) {
				return fromFunction(a -> domainPredicate.test(a) || a.equals(domainValue), 
						            a -> a.equals(domainValue) ? codomainValue: function.apply(a) );
			}
		};
	}

}
