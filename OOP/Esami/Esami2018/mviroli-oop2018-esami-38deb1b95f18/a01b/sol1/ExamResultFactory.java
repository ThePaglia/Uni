package a01b.sol1;

public interface ExamResultFactory {
	
	ExamResult failed();
	
	ExamResult retired();
		
	ExamResult succeededCumLaude();
	
	ExamResult succeeded(int evaluation);

}
