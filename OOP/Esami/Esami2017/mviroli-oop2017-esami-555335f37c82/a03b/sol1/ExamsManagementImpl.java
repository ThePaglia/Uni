package a03b.sol1;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExamsManagementImpl implements ExamsManagement {
	
	private Set<Student> students = new HashSet<>();
	
	private static <T> T searchInSet(Set<T> set, Predicate<T> predicate) {
		return set.stream().filter(predicate).findAny().get();
	}
	
	private Student studentById(int studentId) {
		return searchInSet(students, e -> e.getId()==studentId);
	}
	
	private boolean studentExists(int studentId) {
		return this.students.stream().anyMatch(e->e.getId()==studentId);
	}
	
	
	private void check(boolean supplier) {
		if (!supplier) {
			throw new IllegalStateException();
		}
	}

	@Override
	public void createStudent(int studentId, String name) {
		check(!studentExists(studentId));
		this.students.add(new StudentImpl(studentId,name));	
	}

	@Override
	public void registerLabEvaluation(int studentId, int evaluation, int exam) {
		check(studentExists(studentId));
		check(!this.studentById(studentId).labEvaluations().containsKey(exam));
		this.studentById(studentId).addLabEvaluation(exam, evaluation);
	}

	@Override
	public void registerProjectEvaluation(int studentId, int evaluation, String projectName) {
		check(studentExists(studentId));
		final Student student = this.studentById(studentId); 
		check(!student.getProjectName().isPresent());
		student.setProject(projectName, evaluation);		
	}

	@Override
	public Optional<Integer> finalEvaluation(int studentId) {
		check(studentExists(studentId));
		final Student student = studentById(studentId);
		if (student.lastLabEvaluation().isPresent() && student.getProjectEvaluation().isPresent()) {
			int v1 = student.lastLabEvaluation().get();
			int v2 = student.getProjectEvaluation().get();
			return Optional.of((int)Math.round(Math.max(v1, v2)*0.6 + Math.min(v1, v2)*0.4));
		} 
		return Optional.empty();
	}

	@Override
	public Map<String, Integer> labExamStudentToEvaluation(int exam) {
		return this.students
				   .stream()
				   .filter(s->s.labEvaluations().containsKey(exam))
				   .collect(Collectors.toMap(Student::getName, s->s.labEvaluations().get(exam)));
	}

	@Override
	public Map<String, Integer> allLabExamStudentToFinalEvaluation() {
		return this.students
				   .stream()
				   .filter(s->finalEvaluation(s.getId()).isPresent())
				   .collect(Collectors.toMap(Student::getName, s->finalEvaluation(s.getId()).get()));
	}

	@Override
	public Map<String, Integer> projectEvaluation(String project) {
		return this.students
				   .stream()
				   .filter(s->s.getProjectName().isPresent())
				   .filter(s->s.getProjectName().get().equals(project))
				   .collect(Collectors.toMap(Student::getName, s->s.getProjectEvaluation().get()));
	}


	
}
