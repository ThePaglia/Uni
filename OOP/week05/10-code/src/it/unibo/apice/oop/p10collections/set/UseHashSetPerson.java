package it.unibo.apice.oop.p10collections.set;

import java.util.*;

public class UseHashSetPerson {
	public static void main(String[] s) {
		// HashSet è un dettaglio, lavorare sempre su Set!
		final Set<Person> set = new HashSet<>();

		// Agggiungo 4 elementi
		set.add(new Person("Rossi", 1960, false));
		set.add(new Person("Bianchi", 1980, true));
		set.add(new Person("Verdi", 1972, false));
		set.add(new Person("Neri", 1968, false));
		System.out.println(set);

		// Testo presenza/assenza di 2 elementi
		final Person p1 = new Person("Rossi", 1960, false);
		final Person p2 = new Person("Rossi", 1961, false);
		System.out.println("Check "+p1+" found "+set.contains(p1));
		System.out.println("Check "+p2+" found "+set.contains(p2));

		// Iterazione: nota, fuori ordine rispetto all'inserimento
		for (final Person p : set) {
			System.out.println("Iterating: "+p+" hash = "+p.hashCode());
		}
	}
}
