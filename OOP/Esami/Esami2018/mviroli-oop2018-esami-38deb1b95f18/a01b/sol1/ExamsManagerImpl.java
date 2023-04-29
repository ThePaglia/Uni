package a01b.sol1;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ExamsManagerImpl implements ExamsManager {

	// a map from calls to maps from students to results
	private final Map<String, Map<String, ExamResult>> map = new HashMap<>();
	
	private static void checkArgument(boolean condition) {
		if (!condition) {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public void createNewCall(String call) {
		checkArgument(!map.containsKey(call));
		this.map.put(call, new HashMap<>());
	}

	@Override
	public void addStudentResult(String call, String student, ExamResult result) {
		checkArgument(map.containsKey(call));
		checkArgument(!map.get(call).containsKey(student));
		this.map.get(call).put(student, result);
	}

	@Override
	public Set<String> getAllStudentsFromCall(String call) {
		checkArgument(map.containsKey(call));
		return this.map.get(call).keySet();
	}

	@Override
	public Map<String, Integer> getEvaluationsMapFromCall(String call) {
		checkArgument(map.containsKey(call));
		return this.map.get(call).entrySet().stream()
				   .filter(e -> e.getValue().getEvaluation().isPresent())
				   .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().getEvaluation().get()));
	}

	@Override
	public Map<String, String> getResultsMapFromStudent(String student) {
		return this.map.entrySet().stream()
				   .filter(e -> e.getValue().containsKey(student))
				   .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().get(student).toString()));
	}
	
	// A solution seeking for many stages, but simple ones
	@Override
	public Optional<Integer> getBestResultFromStudent(String student) {
		return this.map.values().stream() // a stream of maps student->result
				   .map(Map::entrySet)    // a stream of sets of entries student->result
				   .flatMap(Set::stream)  // a flattened stream of entries
			       .filter(e -> e.getKey().equals(student)) // just consider my student
			       .map(Entry::getValue) // stream of results
			       .filter(res -> res.getEvaluation().isPresent()) // only those with evaluation
			       .map(ExamResult::getEvaluation) // only evaluations (boxed in optional)
			       .map(Optional::get) // only evaluations (as int)
			       .max((x,y)->x-y); // max
	}

}
