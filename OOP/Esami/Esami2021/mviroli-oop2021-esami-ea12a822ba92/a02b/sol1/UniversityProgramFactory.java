package a02b.sol1;

public interface UniversityProgramFactory {
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have 60 credits
	 */
	UniversityProgram flexible();
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have 60 credits
	 * - MATH courses have >= 12 credits
	 * - COMPUTER_SCIENCE courses have >= 12 credits
	 * - PHYSICS courses have >= 12 credits
	 */
	UniversityProgram scientific();
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have >=48 credits
	 * - COMPUTER_SCIENCE and COMPUTER_ENGINEERING together have >= 30 credits
	 */
	UniversityProgram shortComputerScience();
	
	/**
	 * @return a program where a selection of courses is ok if:
	 * - overall they have =120 credits
	 * - COMPUTER_SCIENCE and COMPUTER_ENGINEERING together have >= 60 credits
	 * - MATH and PHYSICS together have <= 18 credits
	 * - THESIS is = 24 credit
	 */
	UniversityProgram realistic();

}
