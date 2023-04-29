package a01b.e1;

import java.util.Optional;

public interface ExamResult {
	
	enum Kind {
		RETIRED, FAILED, SUCCEEDED
	}
	
	Kind getKind();
	
	Optional<Integer> getEvaluation();
	
	boolean cumLaude();
}
