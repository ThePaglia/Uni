package ex2014.a03b.sol1;

import static org.junit.Assert.*;

import java.io.*;

public class Test {
	
	/*
	 * Implementare l'interfaccia DiceStatistics data tramite una classe DiceStatisticsImpl con costruttore senza argomenti.
	 * Tale classe realizza un generatore di una sequenza di risultati di tiri di N dadi, da scrivere su file uno per uno. Ad esempio, se
	 * N = 3, e si decidono di scrivere 1000 risultati, si dovrà per 1000 volte generare tre interi random compresi fra 1 a 6,
	 * e sommarli fra loro (dà un numero compreso fra 3 e 18). Per generare i numeri si consideri che il metodo java.util.Random.nextInt,
	 * se chiamato con argomento 6, genera un numero compreso fra 0 e 5 incluso. La classe fornisce anche dei metodi per rileggere la sequenza 
	 * ed estrarre informazioni varie (il numero di tiri fortunati -- ossia con tutti i dadi che danno 6, il numero di tiri che danno un valore 
	 * finale dispari, ed una mappa che assegna ad ogni risultato il nuemro di volte che è stato ottenuto).
	 * Il commento al codice di DiceStatistics, e il metodo di test qui sotto costituiscono una spiegazione aggiuntiva del 
	 * problema.
	 * 
	 * IMPORTANTE: 5 punti in questo esercizio saranno attribuiti a chi riesce a costruire una soluzione ad alto livello di qualitò,
	 * in particolare relativamente all'assenza di ogni ripetizione di codice, usando codice "ben progettato", ossia scegliendo con 
	 * cura il pattern da usare.
    */

	@org.junit.Test
	public void testOK() throws IOException{
		String filename = System.getProperty("user.home")+System.getProperty("file.separator")+"stat.dat";
		System.out.println("Using file: "+filename);
		System.out.println();
		
		DiceStatistics ds = new DiceStatisticsImpl();
		ds.setFileName(filename);
		// Genero una sequenza di 1000 risultati di tiri di 2 dadi
		long dim;
		dim = 1000;
		ds.storeSeries(dim,2); // 1000 tiri di 2 dadi
		System.out.println("Working with dim "+dim+" and 2 dice");
		// Ottengo informazioni varie e le stampo a video..
		System.out.println("Odd: "+ds.loadToOddCount()); // quasi 500
		System.out.println("Lucky: "+ds.loadToLuckyCount()); // circa 30.
		System.out.println("Map(3): "+ds.loadToMap()); // più o meno {2=29, 3=47, 4=87, 5=122, 6=141, 7=166, 8=140, 9=109, 10=81, 11=43, 12=24}
		System.out.println();
		
		// Stessa cosa con una nuova serie di 100000 risultati di tiri di 3 dadi
		dim = 100000;
		ds.storeSeries(dim,3); // 100000 tiri di 3 dadi
		System.out.println("Working with dim "+dim+" and 3 dice");
		System.out.println("Odd: "+ds.loadToOddCount()); // quasi 50000
		System.out.println("Lucky: "+ds.loadToLuckyCount()); // circa 462.
		System.out.println("Map(3): "+ds.loadToMap()); // più o meno {3=450, 4=1317, 5=2798, 6=4623, ..., 17=1418, 18=441}

		System.out.println();
		
		// Test considerando margini di errore accettabili
		assertTrue(ds.loadToOddCount()>49000 && ds.loadToOddCount()<51000);
		assertTrue(ds.loadToLuckyCount()>400 && ds.loadToLuckyCount()<500);
		assertEquals(ds.loadToMap().size(),16);
		assertTrue(ds.loadToMap().get(3)>400 && ds.loadToMap().get(3)<500);
		assertTrue(ds.loadToMap().get(18)>400 && ds.loadToMap().get(18)<500);
		assertTrue(ds.loadToMap().get(10)>12000 && ds.loadToMap().get(10)<13000);
		
		
	}
}
