package a02b.sol1;

import java.util.Set;

/**
 * It models a university program (corso di laurea), essentially with a method 'isValid' to check
 * if a selection of courses (curriculum) is compliant with this program
 */
public interface UniversityProgram {
	
	/**
	 * The possible sector of a course
	 */
	enum Sector {
		COMPUTER_SCIENCE, COMPUTER_ENGINEERING, MATHEMATICS, PHYSICS, THESIS
	}
	
	/**
	 * Add information about a new course ("OOP", "SISOP",...) 
	 * 
	 * @param name is the unique (short) name of the course
	 * @param sector is the course sector
	 * @param credits is the number of CFUs of the exam (typically 6 or 12, or even more for the thesis)
	 * (see method Test.fillProgram for its usage
	 */
	void addCourse(String name, Sector sector, int credits);	
	
	/**
	 * Given a selection of courses (curriculum), it states if this is valid in current university program.
	 * For instance, in a engineering program there needs to be a sufficient number
	 * of computer engineering courses, not to much physics, and so on 
	 * 
	 * @param courseNames, gives the selection of courses
	 * @return whether this selection is ok in this program
	 */
	boolean isValid(Set<String> courseNames);
}
