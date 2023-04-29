package it.unibo.apice.oop.p13advanced.enums.enumeration;

import static it.unibo.apice.oop.p13advanced.enums.enumeration.Region.*;

import java.util.*;


public class UseRegion {
	public static void main(String[] args) {
		final List<Region> list = new ArrayList<>();
		// 4 modi di ottenere una Regione
		list.add(Region.LOMBARDIA);
		list.add(SARDEGNA);
		list.add(Region.valueOf("SICILIA"));
		list.add(Region.values()[10]);
		
		for (final Region r: list){
			System.out.println("toString "+r); // LOMBARDIA,...,MOLISE
			System.out.println("ordinale "+r.ordinal()); // 8, 13, 14, 10
			System.out.println("nome "+r.name()); // LOMBARDIA,...,MOLISE
			System.out.println("---");
		}
		
		for (final Region r: Region.values()){
			System.out.print(r+" "); // Stampa tutte le regioni
		}

	}
}
