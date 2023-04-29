package a02b.sol1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.*;

public abstract class AbstractUniversityProgram implements UniversityProgram {
	
	// could have been a Map<String,Pair<Sector,Integer>>
	private final Map<String,Course> coursesMap = new HashMap<>();
	
	@Override
	public void addCourse(String name, Sector sector, int credits) {
		if (this.coursesMap.containsKey(name)) {
			throw new IllegalArgumentException();
		}
		this.coursesMap.put(name, new Course(name, sector, credits));
	}

	@Override
	public boolean isValid(Set<String> courseNames) {
		Set<Course> courses = courseNames.stream().map(this.coursesMap::get).collect(Collectors.toSet());
		return this.getConstraints().stream().allMatch(constraint -> this.isConstraintSatisfied(constraint, courses));
	}
	
	private boolean isConstraintSatisfied(Pair<Predicate<Sector>,Predicate<Integer>> constraint, Set<Course> courses) {
		return constraint.get2().test(courses.stream()
				.filter(c -> constraint.get1().test(c.getSector()))
				.mapToInt(Course::getCredits)
				.sum());
	}

	protected abstract Set<Pair<Predicate<Sector>,Predicate<Integer>>> getConstraints();
	
	private static class Course {
		private String name;
		private Sector sector;
		private int credits;
		
		public Course(String name, Sector sector, int credits) {
			super();
			this.name = name;
			this.sector = sector;
			this.credits = credits;
		}

		public String getName() {
			return name;
		}

		public Sector getSector() {
			return sector;
		}

		public int getCredits() {
			return credits;
		}

		@Override
		public int hashCode() {
			return Objects.hash(credits, name, sector);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Course other = (Course) obj;
			return credits == other.credits && Objects.equals(name, other.name)
					&& sector == other.sector;
		}

		@Override
		public String toString() {
			return "Course [name=" + name + ", sector=" + sector + ", credits="
					+ credits + "]";
		}
	}
}
