package a02b.sol1;

import static a02b.sol1.UniversityProgram.Sector.*;

import java.util.Set;
import java.util.function.Predicate;

import a02b.sol1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {
	
	private static Pair<Predicate<Sector>,Predicate<Integer>> constraint(Predicate<Sector> p1, Predicate<Integer> p2) {
		return new Pair<>(p1,p2);
	}
	
	@Override
	public UniversityProgram flexible() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Sector>,Predicate<Integer>>> getConstraints() {
				return Set.of(
					constraint(sector -> true, credits -> credits == 60)
				);
			}
		};
	}
	
	@Override
	public UniversityProgram scientific() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Sector>,Predicate<Integer>>> getConstraints() {
				return Set.of(
						constraint(sector -> true, credits -> credits == 60),
						constraint(sector -> sector == MATHEMATICS, credits -> credits >= 12),
						constraint(sector -> sector == COMPUTER_SCIENCE, credits -> credits >= 12),
						constraint(sector -> sector == PHYSICS, credits -> credits >= 12)
				);
			}
		};
	}

	@Override
	public UniversityProgram shortComputerScience() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Sector>,Predicate<Integer>>> getConstraints() {
				return Set.of(
						constraint(sector -> true, credits -> credits >= 48),
						constraint(sector -> sector == COMPUTER_SCIENCE || sector == COMPUTER_ENGINEERING, credits -> credits >= 30)
				);
			}
		};
	}

	@Override
	public UniversityProgram realistic() {
		return new AbstractUniversityProgram() {

			@Override
			protected Set<Pair<Predicate<Sector>,Predicate<Integer>>> getConstraints() {
				return Set.of(
						constraint(sector -> true, credits -> credits == 120),
						constraint(sector -> sector == COMPUTER_ENGINEERING | sector == COMPUTER_SCIENCE, credits -> credits >= 60),
						constraint(sector -> sector == MATHEMATICS | sector == PHYSICS, credits -> credits <= 18),
						constraint(sector -> sector == THESIS, credits -> credits == 24)
				);
			}
		};
	}
}
