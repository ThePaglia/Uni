package ex2014.a03.e1;

import static org.junit.Assert.*;
import java.io.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia RandomStatistics data tramite una classe RandomStatisticsImpl con costruttore senza argomenti.
	 * Tale classe realizza un generatore di numeri reali random (reali fra 0 e 1, generati con Math.random) da scrivere su file, 
	 * e poi da rileggere ogni volta per estrarre informazioni varie (il massimo valore, quanti numeri sono sotto una soglia, 
	 * o una lista che descrive come gli elementi si dispongono in certe soglie, ad esempio in 0-0.2, 0.2-0.4, 0.4-0.6, 0.6-0-8, 0.8-1)
	 * Il commento al codice di RandomStatistics, e il metodo di test qui sotto costituiscono una spiegazione aggiuntiva del 
	 * problema.
	 * 
	 * IMPORTANTE: 5 punti in questo esercizio saranno attribuiti a chi riesce a costruire una soluzione ad alto livello di qualitò,
	 * in particolare relativamente all'assenza di ogni ripetizione di codice, usando codice "ben progettato", ossia scegliendo con 
	 * cura il pattern da usare.
	 * 
	 *  Togliere il commento dal test sottostante
	 */

	/*
	@org.junit.Test
	public void testOK() throws IOException{
		String filename = System.getProperty("user.home")+System.getProperty("file.separator")+"stat.dat";
		System.out.println("Using file: "+filename);
		System.out.println();
		
		RandomStatistics rs = new RandomStatisticsImpl();
		rs.setFileName(filename);
		// Genero una serie di 1000 numeri random
		long dim;
		dim = 1000;
		rs.storeSeries(dim);
		System.out.println("Working with dim: "+dim);
		// Ottengo informazioni varie e le stampo a video..
		System.out.println("Max: "+rs.loadToMax()); // quasi 1
		System.out.println("Count(<0.8): "+rs.loadToCountSmaller(0.8)); // quasi 800
		System.out.println("List(3): "+rs.loadToList(3)); // quasi [333,333,334]
		System.out.println();
		
		// Stessa cosa con una nuova serie di 100000 numeri random
		dim = 100000;
		rs.storeSeries(dim);
		System.out.println("Working with dim: "+dim);
		System.out.println("Max: "+rs.loadToMax()); // quasi 1
		System.out.println("Count(<0.5): "+rs.loadToCountSmaller(0.5)); // quasi 50000
		System.out.println("List(5): "+rs.loadToList(5)); // quasi [20000,20000,20000,20000,20000]
		System.out.println();
		
		// Test considerando margini di errore accettabili
		assertTrue(rs.loadToMax()>0.99);
		assertTrue(rs.loadToCountSmaller(0.5)>49000 && rs.loadToCountSmaller(0.5)<51000);
		assertEquals(rs.loadToList(5).size(),5);
		assertTrue(rs.loadToList(5).get(0)>19000 && rs.loadToList(5).get(0)<21000);
		assertTrue(rs.loadToList(5).get(1)>19000 && rs.loadToList(5).get(0)<21000);
		assertTrue(rs.loadToList(5).get(2)>19000 && rs.loadToList(5).get(0)<21000);
		assertTrue(rs.loadToList(5).get(3)>19000 && rs.loadToList(5).get(0)<21000);
		assertTrue(rs.loadToList(5).get(4)>19000 && rs.loadToList(5).get(0)<21000);
		
	}
	*/
}
