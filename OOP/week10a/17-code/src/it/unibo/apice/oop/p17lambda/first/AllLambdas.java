package it.unibo.apice.oop.p17lambda.first;

import java.util.*;

public class AllLambdas {

	private static int mycompare(final String a, final String b) { 
		return a.compareTo(b);
	}

	public static void main(String[] args) {
		final List<String> list = List.of("a", "bb", "c", "ddd");

		// sintassi completa
		Collections.sort(list, (String a, String b) -> { 
			return a.length() - b.length();
		});
		System.out.println(list); // [a, c, bb, ddd]

		// sintassi con type inference
		Collections.sort(list, (a, b) -> {
			return a.length() - b.length();
		});
		System.out.println(list); // [a, c, bb, ddd]

		// sintassi con single-expression body
		Collections.sort(list, (a, b) -> a.length() - b.length()); 
		System.out.println(list); // [a, c, bb, ddd]

		// il body è una chiamata di metodo diretta: userò un method reference
		Collections.sort(list, (a, b) -> mycompare(a, b));
		System.out.println(list); // [a, c, bb, ddd]
	}
}
