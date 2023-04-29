package a03a.e1;

import java.util.*;

/**
 * An interface with operations to manage exams. Exams can be created. When one has been started, evaluations can be registered, until it is finished.
 * Exams are started using an incrementing id.
 * Evaluations of past exams are maintained and accessible.
 */
public interface ExamsManagement {
	
	/**
	 * It creates a new student with given (unique) id and name
	 * @param studentId
	 * @param name
	 */
	void createStudent(int studentId, String name);
	
	/**
	 * It creates a new exam with given (unique) name and an id, that should be increasing coherently with passage of time
	 * (that is, new exams have greater ids)
	 * @param examName
	 * @param incrementalId
	 */
	void createExam(String examName, int incrementalId);
	
	/**
	 * Registers an existing student to an existing exam
	 * @param examName
	 * @param studentId
	 */
	void registerStudent(String examName, int studentId);
	
	/**
	 * Starts a new exam with given name, to which all registrations will be assigned 
	 * @param examName
	 * @throws IllegalStateException if a an exame with lower id was started in the past
	 */
	void examStarted(String examName);

	/**
	 * Gives an evaluation (0..30) to a student regularly registered at current exam
	 * @param studentId
	 * @param evaluation
	 */
	void registerEvaluation(int studentId, int evaluation);
	
	
	/**
	 * Completes current exam 
	 */
	void examFinished();
	
	/**
	 * @param examName
	 * @return all ids of students registered to the exam (even if they got no evaluation)
	 */
	Set<Integer> examList(String examName);
	
	/**
	 * @param studentId
	 * @return the last evaluation received by a given student (the one given in the exam with maximum incrementalId)
	 */
	Optional<Integer> lastEvaluation(int studentId);
	
	/**
	 * @param examName
	 * @return a map from student names to the evaluation (if any) received into a given exam
	 */
	Map<String,Integer> examStudentToEvaluation(String examName);
	
	/**
	 * @param examName
	 * @return a map from evaluations to the number (>0) of students who took it at a given exam
	 */
	Map<Integer,Integer> examEvaluationToCount(String examName);

}
