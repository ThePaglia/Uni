package a03a.sol1;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import a03a.sol1.Evaluation.*;

public class EvaluationBuilderImpl implements EvaluationBuilder {

	private final Map<Pair<String, Integer>, Map<Question, Result>> map = new HashMap<>();
	private boolean built = false;

	@Override
	public EvaluationBuilder addEvaluationByMap(String course, int student, Map<Question, Result> results) {
		if (this.map.containsKey(new Pair<>(course, student))) {
			throw new IllegalStateException("Already added");
		}
		if (results.size() != Question.values().length) {
			throw new IllegalArgumentException("not complete");
		}
		this.map.put(new Pair<>(course, student), new EnumMap<>(results));
		return this;
	}

	@Override
	public EvaluationBuilder addEvaluationByResults(String course, int student, Result resOverall, Result resInterest, Result resClarity) {
		Map<Question, Result> results = new EnumMap<>(Question.class);
		results.put(Question.OVERALL, resOverall);
		results.put(Question.INTEREST, resInterest);
		results.put(Question.CLARITY, resClarity);
		return this.addEvaluationByMap(course, student, results);
	}

	@Override
	public Evaluation build() {
		if (built) {
			throw new IllegalStateException("Already built");
		}
		built = true;
		return new Evaluation() {

			@Override
			public Map<Question, Result> results(String course, int student) {
				return Collections.unmodifiableMap(map.getOrDefault(new Pair<>(course, student), new HashMap<>()));
			}
			
			@Override
			public Map<Result, Long> resultsCountForCourseAndQuestion(String course, Question question) {
				return map.entrySet().stream()
						             .filter(e -> e.getKey().getX().equals(course))
						             .map(Entry::getValue)
						             .map(Map::entrySet)
						             .flatMap(Set::stream)
						             .filter(e -> e.getKey() == question)
						             .collect(Collectors.groupingBy(e -> e.getValue(), Collectors.counting()));
			}

			@Override
			public Map<Result, Long> resultsCountForStudent(int student) {
				return map.entrySet().stream()
						             .filter(e -> e.getKey().getY().equals(student))
						             .map(Entry::getValue)
						             .map(Map::entrySet)
						             .flatMap(Set::stream)
						             .collect(Collectors.groupingBy(e -> e.getValue(), Collectors.counting()));
			}

			@Override
			public double coursePositiveResultsRatio(String course, Question question) {
				List<Result> results = map.entrySet().stream()
						                             .filter(e -> e.getKey().getX().equals(course))
						                             .map(Entry::getValue)
										             .map(Map::entrySet)
										             .flatMap(Set::stream)
						                             .filter(e -> e.getKey() == question)
						                             .map(Entry::getValue)
						                             .collect(Collectors.toList());
				return results.stream().filter(e -> e == Result.FULLY_POSITIVE || e == Result.WEAKLY_POSITIVE).count() / (double) results.size();
			}

		};
	}

}
