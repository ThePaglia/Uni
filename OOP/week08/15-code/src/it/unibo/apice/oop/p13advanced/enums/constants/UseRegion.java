package it.unibo.apice.oop.p13advanced.enums.constants;

import java.util.Arrays;

public class UseRegion {
	
	public static void main(String[] args) {
		// nella variabile regione, si possono usare solo 3 casi
		Region region = Region.MARCHE;
		
		System.out.println(region);
		// si ottengono gli array dei valori possibile
		System.out.println(Arrays.toString(Region.VALUES));
		// è possibile accedere alla "prossima regione"
		System.out.println(Region.VALUES[region.getId()+1]);
	}
}
