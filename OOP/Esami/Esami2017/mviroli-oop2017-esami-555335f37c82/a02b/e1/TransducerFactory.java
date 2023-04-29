package a02b.e1;

public interface TransducerFactory {
	
	/**
	 * @param inputSize: the number of consecutive elements to use to produce a single output
	 * @return a transducer that takes the next inputSize elements and produces the concatenation of their toString; if
	 * the input is closed with less than inputSize elements, then it returns the concatenation of the remaining ones
	 * 
	 * Example with inputSize = 3:
	 * - input: 1,2,3,4,5,6,7
	 * - output: "123","456","7"
	 */
	<X> Transducer<X,String> makeConcatenator(int inputSize);
	
	/**
	 * @return a transducer that takes the next two integers and produces their sum; if
	 * the input is closed with one remaining element, this is produced
	 * 
	 * Example with inputSize = 3:
	 * - input: 1,2,3,4,5,6,7
	 * - output: 3,7,11,7
	 */
	Transducer<Integer,Integer> makePairSummer();
}
