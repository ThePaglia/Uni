package a03a.sol1;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ExamsManagementImpl implements ExamsManagement {
	
	private Set<Exam> exams = new HashSet<>();
	private Set<Student> students = new HashSet<>();
	private Set<Registration> registrations = new HashSet<>();
	private Optional<Exam> currentExam = Optional.empty();
	private Optional<Exam> lastExam = Optional.empty();
	
	private static <T> T searchInSet(Set<T> set, Predicate<T> predicate) {
		return set.stream().filter(predicate).findAny().get();
	}
	
	private Exam examByName(String examName) {
		return searchInSet(exams,e -> e.getName().equals(examName));
	}

	private Student studentById(int studentId) {
		return searchInSet(students, e -> e.getId()==studentId);
	}
	
	private Registration registrationByExamAndStudentId(Exam exam, int studentId) {
		return searchInSet(registrations, r -> r.getExam().equals(exam) && r.getStudent().getId()==studentId);
	}
	
	private void check(boolean supplier) {
		if (!supplier) {
			throw new IllegalStateException();
		}
	}


	@Override
	public void createStudent(int studentId, String name) {
		this.students.add(new Student(studentId,name));
	}

	@Override
	public void createExam(String examName, int incrementalId) {
		this.exams.add(new Exam(incrementalId,examName));
	}

	@Override
	public void registerStudent(String examName, int studentId) {
		this.registrations.add(new Registration(examByName(examName),studentById(studentId)));
	}
	
	@Override
	public void examStarted(String examName) {
		final Exam exam = this.examByName(examName);
		this.check(!this.currentExam.isPresent());
		this.check(!this.lastExam.isPresent() || this.lastExam.get().getId() < exam.getId()); 
		this.currentExam = Optional.of(this.examByName(examName));	
	}

	@Override
	public void registerEvaluation(int studentId, int evaluation) {
		this.check(this.currentExam.isPresent());
		this.registrationByExamAndStudentId(currentExam.get(), studentId).registerEvaluation(evaluation);
	}

	@Override
	public void examFinished() {
		this.check(this.currentExam.isPresent());
		this.lastExam = this.currentExam;
		this.currentExam = Optional.empty();

	}

	@Override
	public Set<Integer> examList(String examName) {
		return registrations.stream()
				            .filter(r -> r.getExam().equals(examByName(examName)))
				            .map(r -> r.getStudent().getId())
				            .collect(Collectors.toSet());
	}

	@Override
	public Optional<Integer> lastEvaluation(int studentId) {
		return registrations.stream()
							.filter(r -> r.getStudent().getId()==studentId)
							.filter(r -> r.getEvaluation().isPresent())
							.max((r1,r2)->r1.getExam().getId()-r2.getExam().getId())
							.flatMap(Registration::getEvaluation);
	}

	@Override
	public Map<String, Integer> examStudentToEvaluation(String examName) {
		return registrations.stream()
							.filter(r -> r.getExam().getName().equals(examName))
							.filter(r -> r.getEvaluation().isPresent())
							.collect(Collectors.toMap(r->r.getStudent().getName(),r->r.getEvaluation().get()));
	}

	@Override
	public Map<Integer, Integer> examEvaluationToCount(String examName) {
		return registrations.stream()
				.filter(r -> r.getExam().getName().equals(examName))
				.filter(r -> r.getEvaluation().isPresent())
				.collect(Collectors.groupingBy(r->r.getEvaluation().get(), Collectors.summingInt(r->1)));
	}

}
