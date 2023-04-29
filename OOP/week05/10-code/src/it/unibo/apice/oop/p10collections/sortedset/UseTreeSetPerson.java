package it.unibo.apice.oop.p10collections.sortedset;

import java.util.*;

public class UseTreeSetPerson {
	public static void main(String[] s) {

		final List<Integer> l = Arrays.asList(new Integer[] { 10, 20, 30, 40 });
		// TreeSet è un dettaglio, lavorare sempre sull'interfaccia

		final NavigableSet<Person> set = new TreeSet<>();
		set.add(new Person("Rossi", 1960, false));
		set.add(new Person("Bianchi", 1980, true));
		set.add(new Person("Verdi", 1972, false));
		set.add(new Person("Neri", 1972, false));
		set.add(new Person("Neri", 1968, false));

		// Iterazione in ordine, poi al contrario, poi fino al 1970
		for (final Person p : set) {
			System.out.println("Iterating: " + p + " hash = " + p.hashCode());
		}
		for (final Person p : set.descendingSet()) {
			System.out.println("Opposite iteration: " + p);
		}
		final Person limit = new Person("", 1970, false);
		for (final Person p : set.headSet(limit, false)) {
			System.out.println("Iterating to 1970: " + p);
		}
	}
}
