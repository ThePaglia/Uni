package a02c.sol1;

import java.util.Set;
import java.util.function.Predicate;
import static a02c.sol1.UniversityProgram.Group.*;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

	@Override
	public UniversityProgram flexible() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
				return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
						new Pair<>(g -> true, c -> c == 48)
				);
			}

		};
	}

	@Override
	public UniversityProgram fixed() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
				return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
						new Pair<>(g -> true, c -> c == 60),
						new Pair<>(g -> g == MANDATORY, c -> c == 12),
						new Pair<>(g -> g == OPTIONAL, c -> c == 36),
						new Pair<>(g -> g == THESIS, c -> c == 12)
				);
			}
		};
	}

	@Override
	public UniversityProgram balanced() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
				return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
						new Pair<>(g -> true, c -> c == 60),
						new Pair<>(g -> g == MANDATORY, c -> c == 24),
						new Pair<>(g -> g == OPTIONAL, c -> c >= 24),
						new Pair<>(g -> g == FREE, c -> c <= 12),
						new Pair<>(g -> g == THESIS, c -> c <= 12)
				);
			}
		};
	}

	@Override
	public UniversityProgram structured() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Group>, Predicate<Integer>>> getConstraints() {
				return Set.<Pair<Predicate<Group>, Predicate<Integer>>>of(
						new Pair<>(g -> true, c -> c == 60),
						new Pair<>(g -> g == MANDATORY, c -> c == 12),
						new Pair<>(g -> g == OPTIONAL_A, c -> c >= 6),
						new Pair<>(g -> g == OPTIONAL_B, c -> c >= 6),
						new Pair<>(g -> g == OPTIONAL_A || g == OPTIONAL_B, c -> c == 30),
						new Pair<>(g -> g == FREE | g == THESIS, c -> c == 18)
				);
			}
		};
	}
}
