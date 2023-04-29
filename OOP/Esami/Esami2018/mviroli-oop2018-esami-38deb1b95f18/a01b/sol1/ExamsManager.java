package a01b.sol1;

import java.util.*;

public interface ExamsManager {
		
	void createNewCall(String call);
	
	void addStudentResult(String call, String student, ExamResult result);
	
	Set<String> getAllStudentsFromCall(String call);
	
	Map<String, Integer> getEvaluationsMapFromCall(String call);
	
	Map<String, String> getResultsMapFromStudent(String student);
	
	Optional<Integer> getBestResultFromStudent(String student);
}
