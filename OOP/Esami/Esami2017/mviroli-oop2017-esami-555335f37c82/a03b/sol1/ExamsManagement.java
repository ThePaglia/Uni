package a03b.sol1;

import java.util.*;

/**
 * An interface with operations to manage exams, in the style of the OOP course. 
 * Students can be created (when they enter the course).
 * Then, as they pass an exam (represented by a unique, incremental id), evaluation is registered.
 * Many lab evaluations can be registered (if they student comes more times, but only the last
 * one, is the significant one), while only one project evaluation can be registered at most.
 * The final evaluation can be computed when both lab and project evaluations are available.
 * This is computed by "weighted average" (60% of the max of the two, 40% of the other),
 * and then using Math.round on the result.
 */
public interface ExamsManagement {
	
	/**
	 * It creates a new student with given (unique) id and name
	 * @param studentId
	 * @param name
	 */
	void createStudent(int studentId, String name);
	
	/**
	 * Registers a given lab evaluation taken at a given exam to a given student
	 * @param studentId
	 * @param evaluation
	 * @param exam
	 */
	void registerLabEvaluation(int studentId, int evaluation, int exam);
	
	/**
	 * Registers a project evaluation, tracking also the project name
	 * @param studentId
	 * @param evaluation
	 * @param project
	 */
	void registerProjectEvaluation(int studentId, int evaluation, String project);
	
	/**
	 * @param studentId
	 * @return (if any) the final evaluation for a student
	 */
	Optional<Integer> finalEvaluation(int studentId);
	
	/**
	 * @param exam
	 * @return a map from student names to the evaluation they received at an exam (only if available)
	 */
	Map<String,Integer> labExamStudentToEvaluation(int exam);
	
	/**
	 * @return a map from student names to the final evaluation they have (only if available)
	 */
	Map<String,Integer> allLabExamStudentToFinalEvaluation();
	
	/**
	 * @param project
	 * @return a map from the student (names) of a project group, to their evaluation at the project
	 */
	Map<String,Integer> projectEvaluation(String project);

}
