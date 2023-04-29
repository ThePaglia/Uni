package a01b.e1;

public interface ExamResultFactory {
	
	ExamResult failed();
	
	ExamResult retired();
		
	ExamResult succeededCumLaude();
	
	ExamResult succeeded(int evaluation);

}
