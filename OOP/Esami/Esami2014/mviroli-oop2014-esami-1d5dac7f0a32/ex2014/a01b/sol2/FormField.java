package ex2014.a01b.sol2;

import java.util.function.Predicate;

public interface FormField{
	String getNome();
	int getLength();
	Predicate<String> getPred();
}
