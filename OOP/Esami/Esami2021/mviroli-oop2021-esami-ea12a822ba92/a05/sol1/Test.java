package a05.sol1;

import static org.junit.Assert.*;

import java.util.Iterator;

import a05.sol1.StateFactory.CounterOp;

public class Test {

	/*
	 * Implementare l'interfaccia StateFactory come indicato nel metodo initFactory qui sotto. 
	 * Realizza una factory per dei State<S,A>, ossia funzioni per rappresentare aggiornamenti ad
	 * uno stato di tipo S, con contestuale estrazione di un valore di uscita di tipo A. 
	 * Quindi uno State<S,A> di fatto è assimilabile ad una funzione pura che prende un S e ritorna
	 * una coppia S,A (nuovo stato + valore di uscita).
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - uno dei quattro metodi della fcatory, a scelta
	 * (ossia, nella parte obbligatoria è sufficiente implementarne 3 dei 4 presenti, il quarto è quindi opzionale) 
	 * 
	 * - la buona progettazione della soluzione, riusando più codice possibile e tenendo tutte le implementazioni
	 * molto succinte
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti
	 * 
	 * - correttezza della parte opzionale: 2 punti (metodo ulteriore)
	 * 
	 * - qualità della soluzione: 5 punti (per la buona progettazione della soluzione, come sopra indicato)
	 * 
	 */

	private StateFactory factory;
	
	@org.junit.Before
	public void initFactory() {
		this.factory = new StateFactoryImpl();
	}
	
	@org.junit.Test
	public void testIncrementingStateWithTransparentOutput() {
		// uno stato che incrementa un valore numerico e lo restituisce ogni volta
		State<Integer, Integer> state = this.factory.fromFunction(i -> new Pair<>(i+1, i+1));
		
		// con stato 0, il prossimo è 1 e anche l'output è 1 
		assertEquals(Integer.valueOf(1), state.nextState(0));
		assertEquals(Integer.valueOf(1), state.value(0));
		
		// con stato 3, il prossimo è 4 e anche l'output è 4 
		assertEquals(Integer.valueOf(4), state.nextState(3));
		assertEquals(Integer.valueOf(4), state.value(3));
	}

	@org.junit.Test
	public void testConstantStateWithStringOutput() {
		// uno stato che non si modifica, e restituisce una rappresentazione a stringa
		State<Integer, String> state = this.factory.fromFunction(i -> new Pair<>(i,String.valueOf(i)));
		
		// con stato 0, il prossimo è 0 e l'output è "0" 
		assertEquals(Integer.valueOf(0), state.nextState(0));
		assertEquals("0", state.value(0));
		
		// con stato 3, il prossimo è 3 e l'output è "3" 
		assertEquals(Integer.valueOf(3), state.nextState(3));
		assertEquals("3", state.value(3));
	}
	
	@org.junit.Test
	public void testMap() {
		// preState è lo stesso stato nel metodo testIncrementingStateWithTransparentOutput()
		State<Integer, Integer> preState = this.factory.fromFunction(i -> new Pair<>(i+1, i+1));
		// state è ottenuto da preState semplicemente convertendo i valori in output a essere stringa 
		State<Integer, String> state = preState.map(i -> String.valueOf(i));
		
		// con stato 0, il prossimo è 1 e l'output è "1" 
		assertEquals(Integer.valueOf(1), state.nextState(0));
		assertEquals("1", state.value(0));
		
		// con stato 3, il prossimo è 4 e l'output è "4" 
		assertEquals(Integer.valueOf(4), state.nextState(3));
		assertEquals("4", state.value(3));
	}
	
	@org.junit.Test
	public void testCompose() {
		// state1 è lo stesso stato nel metodo testIncrementingStateWithTransparentOutput()
		State<Integer, Integer> state1 = this.factory.fromFunction(i -> new Pair<>(i+1, i+1));
		// state2 è lo stato che raddoppia un valore numerico e lo restituisce come stringa
		State<Integer, String> state2 = this.factory.fromFunction(i -> new Pair<>(i*2, String.valueOf(i*2)));
		// la composizione dei due realizza s-> (s+1)*2, e restituisce la rappresentazione a stringa
		State<Integer, String> state = this.factory.compose(state1,  state2);
		
		// con stato 0, il prossimo è 2 e l'output è "2" 
		assertEquals(Integer.valueOf(2), state.nextState(0));
		assertEquals("2", state.value(0));
		
		// con stato 3, il prossimo è 8 e l'output è "8" 
		assertEquals(Integer.valueOf(8), state.nextState(3));
		assertEquals("8", state.value(3));
	}
	
	@org.junit.Test
	public void testIterator() {
		// state è lo stesso stato nel metodo testIncrementingStateWithTransparentOutput()
		State<Integer, String> state = this.factory.fromFunction(i -> new Pair<>(i+1,String.valueOf(i+1)));
		// l'iteratore produce i valori di output ottenuti chiamando la next iterativamente, ossia: 1,2,3,4,...
		Iterator<String> it = state.iterator(0);
		assertEquals("1", it.next());
		assertEquals("2", it.next());
		assertEquals("3", it.next());
	}
	
	@org.junit.Test
	public void testIncSquareHalve() {
		// un iteratore che parte da 1, e ad ogni passo applica s -> square(s+1)/2, mostrandone la stringa
		// idealmente sarebbe da produrre componendo tre stati...
		Iterator<String> it = this.factory.incSquareHalve().iterator(1);
		assertEquals(String.valueOf(2), it.next()); // (1+1)^2/2
		assertEquals(String.valueOf(4), it.next()); // (2+1)^2/2
		assertEquals(String.valueOf(12), it.next()); // (4+1)^2/2
		assertEquals(String.valueOf(84), it.next()); // (12+1)^2/2
	}
	
	@org.junit.Test
	public void testCounter() {
		// una applicazione degli State per modellare oggetti con stato
		// state rappresenta un incremento
		var state = this.factory.counterOp(CounterOp.INC);
		// state rappresenta un incremento seguito da un incremento 
		state = this.factory.compose(state, this.factory.counterOp(CounterOp.INC));
		// state rappresenta un incremento seguito da un incremento seguito da una get 
		state = this.factory.compose(state, this.factory.counterOp(CounterOp.GET));
		// state quindi rappresenta uno stato che si aggiorna incrementando di 2
		assertEquals(Integer.valueOf(2), state.value(0));
		state = this.factory.compose(state, this.factory.counterOp(CounterOp.RESET));
		state = this.factory.compose(state, this.factory.counterOp(CounterOp.GET));
		// state quindi rappresenta uno stato che si aggiorna incrementando di 2, poi resetta, poi emette il valore 0
		assertEquals(Integer.valueOf(0), state.value(0));
	}
}