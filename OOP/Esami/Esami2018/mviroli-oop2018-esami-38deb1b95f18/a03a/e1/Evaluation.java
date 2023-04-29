package a03a.e1;

import java.util.Map;

/**
 * An interface modelling the results of course evaluations at UNIBO, namely, "questionari della didattica"
 */
public interface Evaluation {
	
	/**
	 * For each course, the student has to reply to the following questions
	 */
	enum Question {
		OVERALL, // ("Sei soddisfatto del corso?"),
		INTEREST, // ("Sei interessato al corso?"),
		CLARITY; //("Il docente spiega in modo chiaro?");
	}
	
	/**
	 * To each question, the student must reply one of the following 4 options/evaluations
	 */
	enum Result {
		FULLY_NEGATIVE, // "Decisamente no", 
		WEAKLY_NEGATIVE, // "Più no che si", 
		WEAKLY_POSITIVE, // "Più si che no",  
		FULLY_POSITIVE; // "Decisamente si"
	}
		
	/**
	 * @param course is the unique course name as a string
	 * @param student is the unique student id
	 * @return the results of evaluations that the specified student gave to the specified course
	 */
	Map<Question,Result> results(String course, int student);
	
	/**
	 * @param course
	 * @param question
	 * @return for a given course and question, a map associating to each possible result how many times it was selected
	 * (Often, counting is expressed as a long, but recall that you can easily convert between int and long with casts).
	 */
	Map<Result,Long> resultsCountForCourseAndQuestion(String course, Question questions);
	
	/**
	 * @param student
	 * @return for a given student, a map associating to each possible result (selected at least once) how many times it was selected
	 */
	Map<Result,Long> resultsCountForStudent(int student);
	
	/**
	 * @param course
	 * @param question
	 * @return given a course and question, the ratio ("percentuale") of (FULLY or WEAKLY) positive results it received (as a double in [0,1])
	 * NOTE: this is an optional method for this exam 
	 */
	double coursePositiveResultsRatio(String course, Question question);
	
}