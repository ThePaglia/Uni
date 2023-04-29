package a02b.sol1;


/**
 * This interface models a transducer from sequences of elements of type X to sequences of elements of type Y.
 * For instance, one could turn sequence: 1,2,3,4,5,6,7,8,9,10 into sequence "12","34","56","78","910".
 * Elements of type X are inserted via the method provideNextInput.
 * As soon as a sufficient number of elements have been inserted, they are used to create an output element ready to be extracted.
 * Extraction of an output elements is done via method getOutputElement and isNextOutputReady.
 * Extraction should not necessarily occur immediately as an output is ready: the transducer is essentially a queue. 
 * The input side of a transducer can be closed via inputIsOver.
 * When nothing more will be extracted, isOutputOver turns to true. 
 */
public interface Transducer<X,Y> {
	 
	/**
	 * Inserts a new element x into the transducer
	 * @param y
	 */
	void provideNextInput(X x);
	
	
	/**
	 * Closes the input part of the transducer, so that no new input can be inserted 
	 */
	void inputIsOver();

	
	/**
	 * @return whether a new output element is ready to be extracted
	 */
	boolean isNextOutputReady();
	
	/**
	 * @return the next element to extract
	 * @throws IllegalStateException if there is no such element, or the output is over
	 */
	Y getOutputElement();
	
	/**
	 * @return whether the output is over (namely, the input is over and all remaining elements have been extracted)
	 */
	boolean isOutputOver();
}
