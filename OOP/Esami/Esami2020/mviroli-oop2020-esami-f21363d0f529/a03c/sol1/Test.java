package a03c.sol1;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	/*
	 * Implementare l'interfaccia TableFactory come indicato nel metodo initFactory
	 * qui sotto. Realizza una factory per delle Table<R,C,V>, ossia strutture dati che modellano 
	 * una tabella con una informazione per riga (tipo R) una per colonna (tipo C): quindi ogni cella
	 * è identificata da un valore di riga, uno di colonna, e ha un valore (tipo V) inserito nella cella.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei test chiamati optionalTestXYZ (relativi al Table.asColumnMap e a TableFactory.squareMatrix) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto e senza ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti
	 *  
	 * - correttezza della parte opzionale: 4 punti
	 *  
	 * - qualità della soluzione: 3 punti
	 * 
	 */

	private TableFactory factory = null;

	@org.junit.Before
	public void initFactory() {
		this.factory = new TableFactoryImpl();
	}

	// lab e project: una enum usata in alcuni test
	enum Esame {
		LAB, PROJECT
	};

	@org.junit.Test
	public void testFromMap() {
		Map<Pair<String, Esame>, Integer> map = new HashMap<>();
		map.put(new Pair<>("Rossi", Esame.LAB), 20);
		map.put(new Pair<>("Rossi", Esame.PROJECT), 28);
		map.put(new Pair<>("Verdi", Esame.LAB), 20);
		map.put(new Pair<>("Casadei", Esame.LAB), 19);
		map.put(new Pair<>("Grandi", Esame.PROJECT), 19);
		// una tabella che associa persone (righe) e tipo d'esame (colonne), ad un voto
		var table = this.factory.fromMap(map);
		assertEquals(Set.of(Esame.LAB, Esame.PROJECT), table.columns()); // colonne
		assertEquals(Set.of("Rossi", "Verdi", "Casadei", "Grandi"), table.rows()); // righe
		assertEquals(Optional.empty(), table.getValue("Casadei", Esame.PROJECT)); // quanto ha preso Casadei nel progetto
		assertEquals(Optional.of(19), table.getValue("Casadei", Esame.LAB)); // quanto ha preso Casadei nell'esame in lab

		table.putValue("Casadei", Esame.PROJECT, 25);  // ora Casadei ha preso 25 nel progetto
		assertEquals(Optional.of(25), table.getValue("Casadei", Esame.PROJECT));

		assertEquals(Map.of(Esame.LAB, 20, Esame.PROJECT, 28), table.asRowMap().get("Rossi")); // i voti di Rossi
		assertEquals(Map.of(Esame.LAB, 20), table.asRowMap().get("Verdi")); // i voti di Verdi
		assertEquals(Map.of(Esame.PROJECT, 19), table.asRowMap().get("Grandi")); // i voti di Grandi

	}
	
	@org.junit.Test
	public void testFromFunction() {
		// la classica "tabellina del *, una tabella con interi (0..9) nelle righe e nelle colonne, e moltiplicazioni nelle celle 
		final Set<Integer> range = Stream.iterate(0,x->x+1).limit(10).collect(Collectors.toSet());
		final Table<Integer,Integer,Integer> table = this.factory.fromFunction(range, range, (i,j)->i*j);
		
		assertEquals(range, table.columns()); // colonne
		assertEquals(range, table.rows()); // righe
		assertEquals(Optional.empty(), table.getValue(100,100)); // 100,100 non ha cella
		assertEquals(Optional.of(54), table.getValue(6,9)); // riga 6, colonna 9 --> valore 54
		
		assertEquals(Set.of(0,5,10,15,20,25,30,35,40,45),new HashSet<>(table.asRowMap().get(5).values())); // 5a riga, la tabellina del 5
	}
	
	@org.junit.Test
	public void testGraph() {
		final Set<Pair<String,String>> edges = Set.of(
				new Pair<>("a","b"), // a->b
				new Pair<>("b","c"), // b->c
				new Pair<>("c","d"), // c->d
				new Pair<>("d","a"), // d->a
				new Pair<>("a","a")  // a->a
		);
		// una tabella che rappresenta il grafo sopra (con true dove c'è arco, false dove non c'è)
		final Table<String,String,Boolean> table = this.factory.graph(edges);
		
		assertEquals(Set.of("a","b","c","d"), table.columns()); // colonne
		assertEquals(Set.of("a","b","c","d"), table.rows()); // righe
		
		assertEquals(Optional.of(true), table.getValue("a","b")); // c'è arco da a a b?
		assertEquals(Optional.of(false), table.getValue("a","c")); // c'è arco da a a c?
		
		assertEquals(4,table.asRowMap().size()); // 4 righe
		assertEquals(4,table.asRowMap().get("a").size()); // la prima riga ha 4 colonne
		assertTrue(table.asRowMap().get("a").get("b"));
		assertFalse(table.asRowMap().get("a").get("c"));
	}
	
	@org.junit.Test
	public void optionalTestArray() {
		final String[][] array = new String[][] {
			new String[] {"a","b"},
			new String[] {"c","d"}
		};
		// una tabella che rappresenta una matrice, con numeri crescenti (indici) in righe e colonne
		final Table<Integer,Integer,String> table = this.factory.squareMatrix(array);
		
		assertEquals(Set.of(0,1), table.columns());
		assertEquals(Set.of(0,1), table.rows());
		
		assertEquals(Optional.of("a"), table.getValue(0,0));
		assertEquals(Optional.of("b"), table.getValue(0,1));
		assertEquals(Optional.of("c"), table.getValue(1,0));
		assertEquals(Optional.of("d"), table.getValue(1,1));
		
		assertEquals(2,table.asRowMap().size());
		assertEquals(2,table.asRowMap().get(0).size());
		assertEquals("a", table.asRowMap().get(0).get(0));
		assertEquals("b", table.asRowMap().get(0).get(1));
	}
	
	@org.junit.Test
	public void optionalTestColumnMap() {
		// come l'esempio nel testFromMap... ma testando la asColumnMap
		Map<Pair<String, Esame>, Integer> map = new HashMap<>();
		map.put(new Pair<>("Rossi", Esame.LAB), 20);
		map.put(new Pair<>("Rossi", Esame.PROJECT), 28);
		map.put(new Pair<>("Verdi", Esame.LAB), 20);
		map.put(new Pair<>("Casadei", Esame.LAB), 19);
		map.put(new Pair<>("Grandi", Esame.PROJECT), 19);
		var table = this.factory.fromMap(map);

		assertEquals(Map.of("Rossi", 20, "Verdi", 20, "Casadei", 19), table.asColumnMap().get(Esame.LAB));
		assertEquals(Map.of("Rossi", 28, "Grandi", 19), table.asColumnMap().get(Esame.PROJECT));
		
		
		// come l'esempio nel testGraph... ma testando la asColumnMap
		final Set<Pair<String,String>> edges = Set.of(
				new Pair<>("a","b"),
				new Pair<>("b","c"),
				new Pair<>("c","d"),
				new Pair<>("d","a"),
				new Pair<>("a","a")
		);
		final Table<String,String,Boolean> table2 = this.factory.graph(edges);
		
		
		assertEquals(4,table2.asColumnMap().size());
		assertEquals(4,table2.asColumnMap().get("a").size());
		assertTrue(table2.asColumnMap().get("a").get("d"));
		assertTrue(table2.asColumnMap().get("a").get("a"));
		assertFalse(table2.asColumnMap().get("a").get("b"));
	}

	
	
}
