package a03a.e1;

import java.util.Map;

import a03a.sol1.Evaluation.*;

/**
 * A builder of ConferenceReviewing, with fluent interface and method build() to be called just once
 */
interface EvaluationBuilder {
	
	/**
	 * @param course
	 * @param student
	 * @param results
	 * @return this, and adds an evaluation: results must provide a result to each question
	 */
	EvaluationBuilder addEvaluationByMap(String course, int student, Map<Question,Result> results);
	
	/**
	 * @param course
	 * @param student
	 * @param results
	 * @return this, and provides a variation of the above method, directly specifying the three results
	 */
	EvaluationBuilder addEvaluationByResults(String course, int student, Result resOverall, Result resInterest, Result resClarity);
	
	/**
	 * @return and builds the ConferenceReviewing
	 */
	Evaluation build();
}

