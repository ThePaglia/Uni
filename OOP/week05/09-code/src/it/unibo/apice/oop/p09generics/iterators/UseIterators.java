package it.unibo.apice.oop.p09generics.iterators;

public class UseIterators {
	public static void main(String[] s) {
		final List<Integer> list = new List<>(1, 
				                	new List<>(2, 
				                	  new List<>(3, null)));

		final Vector<Integer> vector = new Vector<>();
		vector.addElement(10);
		vector.addElement(20);

		// creo 3 iteratori..
		final Iterator<Integer> iterator = new IntRangeIterator(5, 10);
		final Iterator<Integer> iterator2 = new ListIterator<>(list);
		final Iterator<Integer> iterator3 = new VectorIterator<>(vector);

		// ne stampo il contenuto..
		printAll(iterator);
		printAll(iterator2);
		printAll(iterator3);
	}

	// e se volessi una printAll che prende anche Iterator<String>??
	static void printAll(Iterator<Integer> iterator) {
		while (iterator.hasNext()) {
			System.out.println("Elemento : " + iterator.next());
		}
	}
}
