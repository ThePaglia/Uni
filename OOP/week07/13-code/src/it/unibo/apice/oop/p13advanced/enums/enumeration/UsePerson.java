package it.unibo.apice.oop.p13advanced.enums.enumeration;

import java.util.*;
import static it.unibo.apice.oop.p13advanced.enums.enumeration.Region.*;

public class UsePerson {
	public static void main(String[] args){
		final List<Person> list = new ArrayList<>();
		list.add(new Person("Mario","Rossi",Region.EMILIA_ROMAGNA));
		list.add(new Person("Gino","Bianchi",Region.SICILIA));
		list.add(new Person("Carlo","Verdi",Region.LOMBARDIA));
		
		// Impossibile commettere errori (tranne null...)
		System.out.println(list.get(0).fromRegion(ABRUZZO));
		// uso dell'import static
		
		// Prestazioni OK!
		for (final Person p: list){
			if (p.isIslander()){
				System.out.println(p);
			}
		}
	}
}
