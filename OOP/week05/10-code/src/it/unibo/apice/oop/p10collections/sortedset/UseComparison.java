package it.unibo.apice.oop.p10collections.sortedset;

import java.util.*;

public class UseComparison {

	public static void main(String[] s) {
		
		System.out.println("abc vs def: " + "abc".compareTo("def")); // neg
		System.out.println("1 vs 2: " + Integer.valueOf(1).compareTo(2));// neg

		final Person p1 = new Person("Rossi", 1960, false);
		final Person p2 = new Person("Rossi", 1972, false);
		final Person p3 = new Person("Bianchi", 1972, false);
		final Person p4 = new Person("Bianchi", 1972, true);

		System.out.println(p1 + " vs " + p2 + ": " + p1.compareTo(p2)); // pos
		System.out.println(p2 + " vs " + p3 + ": " + p2.compareTo(p3)); // pos
		System.out.println(p3 + " vs " + p4 + ": " + p3.compareTo(p4)); // zero
		
		System.out.println(new TreeSet<Integer>(Arrays.asList(4,3,2,1)));
		 // 1,2,3,4
	}
}
