package a02c.sol1;

public interface UniversityProgramFactory {
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have 48 credits
	 */
	UniversityProgram flexible();
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have 60 credits
	 * - mandatory courses have =12 credits
	 * - optional courses have =36 credits
	 * - thesis has =12 credits
	 */
	UniversityProgram fixed();
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have 60 credits
	 * - mandatory courses have =24 credits
	 * - optional courses have >=24 credits
	 * - free courses have <=12 credits
	 * - thesis has <=12 credits
	 */
	UniversityProgram balanced();
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have 60 credits
	 * - mandatory courses have =12 credits
	 * - optional_a courses have >=6 credits
	 * - optional_b courses have >=6 credits
	 * - optional_a and optional_b courses have together =30 credits
	 * - free and thesis have together =18 credits
	 */
	UniversityProgram structured();

}
