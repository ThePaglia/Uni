package a03a.e1;

import static org.junit.Assert.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

	/*
	 * Implementare l'interfaccia GroupingFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei Grouping<G,V>, ossia
	 * delle strategie per raggruppare dati (tipo V) in gruppi (tipo G) -- un dato
	 * sta in un solo gruppo.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei test chiamati optionalTestXYZ (relativi a
	 * Grouping.combineGroups e GroupingFactory.fromFunction) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto e
	 * che eviti ripetizioni
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

	private GroupingFactory factory = null;

	@org.junit.Before
	public void initFactory() {
		//this.factory = new GroupingFactoryImpl();
	}

	// Pari, dispari, negativi: una enum usata in alcuni test
	enum IntegerType {
		EVEN, ODD, NEGATIVE
	};

	@org.junit.Test
	public void testFromPairs() {
		// appartenenze dei numeri in [-3,8] alle tre tipologie
		Set<Pair<IntegerType, Integer>> pairs = Set.of(new Pair<>(IntegerType.EVEN, 0), new Pair<>(IntegerType.EVEN, 2),
				new Pair<>(IntegerType.EVEN, 4), new Pair<>(IntegerType.EVEN, 6), new Pair<>(IntegerType.EVEN, 8),
				new Pair<>(IntegerType.ODD, 1), new Pair<>(IntegerType.ODD, 3), new Pair<>(IntegerType.ODD, 5),
				new Pair<>(IntegerType.ODD, 7), new Pair<>(IntegerType.NEGATIVE, -1),
				new Pair<>(IntegerType.NEGATIVE, -2), new Pair<>(IntegerType.NEGATIVE, -3));
		Grouping<IntegerType, Integer> grouping = this.factory.fromPairs(pairs);

		// i tre gruppi
		assertEquals(Set.of(IntegerType.EVEN, IntegerType.ODD, IntegerType.NEGATIVE), grouping.getGroups());

		// il gruppo di 0,1, e -1... e 100 (che non c'è)
		assertEquals(Optional.of(IntegerType.EVEN), grouping.getGroupOf(0));
		assertEquals(Optional.of(IntegerType.ODD), grouping.getGroupOf(1));
		assertEquals(Optional.of(IntegerType.NEGATIVE), grouping.getGroupOf(-1));
		assertEquals(Optional.empty(), grouping.getGroupOf(100));

		// i valori del gruppo "pari"
		assertEquals(Set.of(0, 2, 4, 6, 8), grouping.getValuesOfGroup(IntegerType.EVEN));

		// test sulla mappa
		assertEquals(3, grouping.asMap().size());
		assertEquals(Set.of(0, 2, 4, 6, 8), grouping.asMap().get(IntegerType.EVEN));
		assertEquals(4, grouping.asMap().get(IntegerType.ODD).size());
	}

	@org.junit.Test
	public void testSingleton() {
		// "a","b","c" fanno tre gruppi, uno ognuno
		Grouping<String, String> grouping = this.factory.singletons(Set.of("a", "b", "c"));

		// i tre gruppi
		assertEquals(Set.of("a", "b", "c"), grouping.getGroups());

		assertEquals(Optional.of("a"), grouping.getGroupOf("a"));
		assertEquals(Optional.of("b"), grouping.getGroupOf("b"));
		assertEquals(Optional.of("c"), grouping.getGroupOf("c"));
		assertEquals(Optional.empty(), grouping.getGroupOf("d"));

		assertEquals(Set.of("a"), grouping.getValuesOfGroup("a"));
		assertEquals(Set.of("b"), grouping.getValuesOfGroup("b"));

		// test sulla mappa
		assertEquals(Set.of("a"), grouping.asMap().get("a"));
		assertEquals(3, grouping.asMap().size());
	}

	@org.junit.Test
	public void testWithChampion() {
		// raggruppiamo i valori da 0 a 99
		Set<Integer> range = IntStream.range(0, 100).mapToObj(i -> i).collect(Collectors.toSet());
		// raggruppiamo i valori con stesso valore di unità (32 e 42 stanno assieme, avendo unità 2), ovvero con
		// stesso resto della divisione per 10
		// il rappresentante di un gruppo è il valore senza decina (il rappresentante del gruppo con unità 2 è 
		// quindi il numero 2)
		Grouping<Integer, Integer> grouping = this.factory.withChampion(range, (i, j) -> i % 10 == j % 10, i -> i < 10);

		// così formiamo 10 gruppi, con questi rappresentanti
		assertEquals(Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), grouping.getGroups());

		assertEquals(Optional.of(2), grouping.getGroupOf(22)); // il gruppo del 22
		assertEquals(Optional.of(6), grouping.getGroupOf(56)); // ..
		assertEquals(Optional.of(0), grouping.getGroupOf(90));
		assertEquals(Optional.empty(), grouping.getGroupOf(100));

		assertEquals(Set.of(2, 12, 22, 32, 42, 52, 62, 72, 82, 92), grouping.getValuesOfGroup(2)); // gli elementi del gruppo 2
		assertEquals(Set.of(6, 16, 26, 36, 46, 56, 66, 76, 86, 96), grouping.getValuesOfGroup(6)); // gli elementi del gruppo 6

		assertEquals(Set.of(2, 12, 22, 32, 42, 52, 62, 72, 82, 92), grouping.asMap().get(2));
		assertEquals(10, grouping.asMap().size());
	}

	@org.junit.Test
	public void optionalTestFromFunction() {
		// Lo stesso identico esempio di testFromPairs, ma con fromFunction
		Set<Integer> range = IntStream.range(-3, 9).mapToObj(i -> i).collect(Collectors.toSet());
		// La funzione associa ad ogni valore il suo gruppo...
		Grouping<IntegerType, Integer> grouping = this.factory.fromFunction(range,
				i -> i < 0 ? IntegerType.NEGATIVE : 
					i % 2 == 0 ? IntegerType.EVEN : IntegerType.ODD);

		// Da qui in poi come testFromPairs
		
		// i tre gruppi
		assertEquals(Set.of(IntegerType.EVEN, IntegerType.ODD, IntegerType.NEGATIVE), grouping.getGroups());

		// il gruppo di 0,1, e -1... e 100 (che non c'è)
		assertEquals(Optional.of(IntegerType.EVEN), grouping.getGroupOf(0));
		assertEquals(Optional.of(IntegerType.ODD), grouping.getGroupOf(1));
		assertEquals(Optional.of(IntegerType.NEGATIVE), grouping.getGroupOf(-1));
		assertEquals(Optional.empty(), grouping.getGroupOf(100));

		// i valori del gruppo "pari"
		assertEquals(Set.of(0, 2, 4, 6, 8), grouping.getValuesOfGroup(IntegerType.EVEN));

		// test sulla mappa
		assertEquals(3, grouping.asMap().size());
		assertEquals(Set.of(0, 2, 4, 6, 8), grouping.asMap().get(IntegerType.EVEN));
		assertEquals(4, grouping.asMap().get(IntegerType.ODD).size());
	}

	@org.junit.Test
	public void optionalTestCombineGroups() {
		// Prendiamo il grouping di testSingleton, e riuniamo il gruppo a e b in un gruppo ab
		Grouping<String, String> grouping = this.factory.singletons(Set.of("a", "b", "c")).combineGroups("a", "b","ab");

		assertEquals(Set.of("ab", "c"), grouping.getGroups());

		assertEquals(Optional.of("ab"), grouping.getGroupOf("a"));
		assertEquals(Optional.of("ab"), grouping.getGroupOf("b"));
		assertEquals(Optional.of("c"), grouping.getGroupOf("c"));

		assertEquals(Set.of("a", "b"), grouping.getValuesOfGroup("ab"));
		assertEquals(Set.of("c"), grouping.getValuesOfGroup("c"));

		assertEquals(Set.of("a", "b"), grouping.asMap().get("ab"));
	}
}
