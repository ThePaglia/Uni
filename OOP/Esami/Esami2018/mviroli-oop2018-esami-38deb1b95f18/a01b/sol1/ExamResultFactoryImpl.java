package a01b.sol1;

import java.util.Optional;

import a01b.sol1.ExamResult.Kind;

/**
 * Note: this solution is more verbose than needed, but it is still a good one since
 * it stresses separation of concepts (SRP principle) and avoidance of repetitions.
 * Alternative good solutions are:
 * - a unique class with a kind, evaluation and boolean field
 * - an enumeration of the 16 possible results 
 */
public class ExamResultFactoryImpl implements ExamResultFactory {
	
	/**
	 * An abstract class common to all implementations of Exams
	 */
	private static abstract class AbstractExamResult implements ExamResult {

		private final Kind kind;
		
		public AbstractExamResult(Kind kind) {
			this.kind = kind;
		}

		@Override
		public Kind getKind() {
			return this.kind;
		}

		@Override
		public Optional<Integer> getEvaluation() {
			return Optional.empty();
		}

		@Override
		public boolean cumLaude() {
			return false;
		}
		
		@Override
		public String toString() {
			return this.kind.toString();
		}
	}
	
	/**
	 * An abstract class common to all implementations of successful Exams. 
	 */
	private static abstract class AbstractSuccessExamResult extends AbstractExamResult{
		
		private final int evaluation;

		public AbstractSuccessExamResult(int evaluation) {
			super(Kind.SUCCEEDED);
			if (evaluation < 18 || evaluation > 30) {
				throw new IllegalArgumentException();
			}
			this.evaluation = evaluation;
		}

		@Override
		public Optional<Integer> getEvaluation() {
			return Optional.of(this.evaluation);
		}
	}
	
	// failed, retired and 30L are better seen as constants
	private static final ExamResult FAILED = new AbstractExamResult(Kind.FAILED){};
	private static final ExamResult RETIRED = new AbstractExamResult(Kind.RETIRED){};
	private static final ExamResult CUMLAUDE = new AbstractSuccessExamResult(30){

		@Override
		public boolean cumLaude() {
			return true;
		}
		
		@Override
		public String toString() {
			return super.toString()+"(30L)";
		}			
	};

	
	@Override
	public ExamResult failed() {
		return FAILED;
	}

	@Override
	public ExamResult retired() {
		return RETIRED;
	}
	
	@Override
	public ExamResult succeededCumLaude() {
		return CUMLAUDE;
	}
	
	/* 
	 * Another simple instantiation of AbstractSuccessExamResult
	 */
	@Override
	public ExamResult succeeded(int evaluation) {
		return new AbstractSuccessExamResult(evaluation){

			@Override
			public String toString() {
				return super.toString()+"("+evaluation+")";
			}			
		};
	}
}
