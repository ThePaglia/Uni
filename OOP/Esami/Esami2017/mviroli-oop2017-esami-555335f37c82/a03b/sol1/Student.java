package a03b.sol1;

import java.util.Map;
import java.util.Optional;

public interface Student {

	int getId();

	String getName();

	void addLabEvaluation(int exam, int evaluation);

	Map<Integer, Integer> labEvaluations();

	void setProject(String projectName, int evaluation);

	Optional<String> getProjectName();

	Optional<Integer> getProjectEvaluation();

	Optional<Integer> lastLabEvaluation();

}