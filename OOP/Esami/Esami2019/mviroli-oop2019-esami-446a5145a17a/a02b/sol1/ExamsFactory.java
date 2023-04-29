package a02b.sol1;

public interface ExamsFactory {
	
	/**
	 * Models the activities for the kind of exam called "Simple"
	 */
	enum SimpleExamActivities {
		WRITTEN, ORAL, REGISTER
	}
	
	/**
	 * @return a CourseExam for a Simple Exam, where one is expected to execute:
	 * WRITTEN, then ORAL, then REGISTER
	 */
	CourseExam<SimpleExamActivities> simpleExam();

	/**
	 * Models the activities for the kind of example called "OOP"
	 */
	enum OOPExamActivities {
		LAB_REGISTER, LAB_EXAM, PROJ_PROPOSE, PROJ_SUBMIT, PROJ_EXAM, FINAL_EVALUATION,
		/* the two below used only in full version, which is optional */ 
		STUDY, CSHARP_TASK
	}
	
	/**
	 * @return a CourseExam for an OOP Exam, where one is expected to execute:
	 * (LAB_REGISTER then LAB_EXAM), and in parallel (PROJ_PROPOSE then PROJ_SUBMIT then PROJ_EXAM)
	 * when both have been done, then one can do FINAL_EVALUATION
	 */
	CourseExam<OOPExamActivities> simpleOopExam();
	
	/**
	 * >>> This method is optional today (OOP, 31/1/2020)!
	 * @return a CourseExam for a full OOP Exam, which is like the above one, plus:
	 * - first need to STUDY, then can proceed
	 * - after PROJ_SUBMIT, one has also to do CSHARP_TASK before actually go to FINAL_EVALUATION
	 */
	CourseExam<OOPExamActivities> fullOopExam();
}
