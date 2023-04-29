package it.unibo.apice.oop.p13advanced.enums.enumeration;

import java.util.*;

public class UseEnum {
	public static void main(String[] args) {
		final List<Region> list = new ArrayList<>();
		
		list.add(Region.LOMBARDIA);
		list.add(Region.PIEMONTE);
		list.add(Region.EMILIA_ROMAGNA);
		
		for (final Region r: list){
			System.out.println(r.toString());
		}
	}
}
