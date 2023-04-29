package a02c.sol1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractUniversityProgram implements UniversityProgram {
	
	private Map<String,Pair<Set<Group>,Integer>> coursesMap = new HashMap<>();
	
	@Override
	public void setCourses(Map<String,Pair<Set<Group>,Integer>> map) {
		this.coursesMap = Collections.unmodifiableMap(map);
	}	
	
	@Override
	public boolean isValid(Set<String> courses) {
		return this.getConstraints().stream().allMatch(c -> this.isConstraintSatisfied(c, courses));
	}
	
	private boolean isConstraintSatisfied(Pair<Predicate<Group>,Predicate<Integer>> constraint, Set<String> courses) {
		int credits = courses.stream()
				.filter(c -> this.coursesMap.get(c).get1().stream().anyMatch(constraint.get1()))
				.mapToInt(c -> this.coursesMap.get(c).get2()).sum();
		System.out.println(courses+" "+credits);
		return constraint.get2().test(credits);
	}

	protected abstract Set<Pair<Predicate<Group>,Predicate<Integer>>> getConstraints();
	
}
