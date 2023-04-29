package a02c.e1;

import java.util.Map;
import java.util.Set;


/**
 * It models a university program (corso di laurea), essentially with a method 'isValid' to check
 * if a selection of courses (curriculum) is compliant with this program. This is done by creating
 * groups of exams (possibly overlapping), and having requirements on the numbers of credits in each 
 * group.
 */
public interface UniversityProgram {
	
	/**
	 * The possible groups of courses
	 */
	enum Group {
		MANDATORY, OPTIONAL, OPTIONAL_A, OPTIONAL_B, FREE, THESIS
	}
	
	/**
	 * Sets the collection of available courses 
	 * 
	 * @param courses is a map from 'course name' to pairs of a 'set of groups' this course belongs to, and
	 * the number of credits/CFUs
	 * (see method Test.fillProgram for its usage)
	 */
	void setCourses(Map<String,Pair<Set<Group>,Integer>> courses);	
	
	/**
	 * Given a selection of courses (curriculum), it states if this is valid in current university program.
	 * For instance, in a 'fixed' curriculum, you need to have 60 CFUs in all groups, exactly 12
	 * mandatory, exactly 12 in thesis, and exactly 36 in optional. 
	 * 
	 * @param courseNames, gives the selection of courses
	 * @return whether this selection is ok in this program
	 */
	boolean isValid(Set<String> courseNames);
}
