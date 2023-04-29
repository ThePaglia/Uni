package it.unibo.apice.oop.p17lambda.first;

import java.util.*;

public class FirstComparable {

	public static void main(String[] args) {
		
		final List<Person> list = new ArrayList<Person>();
		list.add(new Person("Mario",1960,true));
		list.add(new Person("Gino",1970,false));
		list.add(new Person("Rino",1951,true));
		System.out.println(list);
		
		// Without lambdas
		Collections.sort(list, new Comparator<Person>(){

			public int compare(Person o1, Person o2) {
				return o1.getBirthYear() - o2.getBirthYear();
			}
			
		});
		System.out.println(list);
		
		// With lambdas
		Collections.sort(list, (o1,o2) -> o2.getBirthYear() - o1.getBirthYear());
		System.out.println(list);
	}

}
