
package a03a.sol1;

import java.util.Optional;

public class Registration {
	private final Exam exam;
	private final Student student;
	private Optional<Integer> evaluation = Optional.empty();

	public Registration(Exam exam, Student student) {
		super();
		this.exam = exam;
		this.student = student;
	}

	public Exam getExam() {
		return exam;
	}

	public Student getStudent() {
		return student;
	}

	public Optional<Integer> getEvaluation() {
		return evaluation;
	}
	
	public void registerEvaluation(int evaluation) {
		this.evaluation = Optional.of(evaluation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exam == null) ? 0 : exam.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registration other = (Registration) obj;
		if (exam == null) {
			if (other.exam != null)
				return false;
		} else if (!exam.equals(other.exam))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Registration [exam=" + exam + ", student=" + student + ", evaluation=" + evaluation + "]";
	}

}
