package ex2014.a01b.sol1;

/**
 * A functional interface, with a method that takes two elements of a sequence (prev and next),
 * and checks if the next is OK with respect to previous.
 * E.g., it could be used to say that the sequence of numbers is increasing.
 */
public interface ProgressiveFilter<X> {

	boolean isNextOK(X previous, X next);
}
