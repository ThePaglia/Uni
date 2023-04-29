package ex2014.a01b.sol1;

import static org.junit.Assert.*;

public class Test {

	/*
	 * Implementare l'interfaccia ProgressiveAcceptor data tramite una classe ProgressiveAcceptorImpl con costruttore senza argomenti.
	 * Il commento al codice di ProgressiveAcceptor, e i tre metodi di test qui sotto costituiscono la necessaria spiegazione del 
	 * problema.
	 */
	
	@org.junit.Test
	public void testOK() {
		ProgressiveAcceptor<Double> pa = new ProgressiveAcceptorImpl<>();
		// aggregherò col "+", ossia sommerò
		pa.setAggregator(new Aggregator<Double>(){

			@Override
			public Double aggregate(Double one, Double two) {
				// TODO Auto-generated method stub
				return one+two;
			}
			
		});
		// accetterò elementi via via crescenti, e quindi la sequenza sarà ordinata in modo crescente
		pa.setProgressiveFilter(new ProgressiveFilter<Double>(){

			@Override
			public boolean isNextOK(Double previous, Double next) {
				return next > previous;
			}
			
		});
		// accetto una sequenza con al massimo 5 elementi 
		pa.setSize(5);
		assertTrue(pa.accept(0, 10.0));
		// fin qui ho inserito 10
		assertEquals(pa.aggregateAll(),new Double(10.0));
		assertTrue(pa.accept(1, 20.0));
		assertTrue(pa.accept(2, 30.0));
		// fin qui ho inserito 10,20,30
		assertEquals(pa.aggregateAll(),new Double(60.0)); // 10+20+30
		assertTrue(pa.accept(3, 40.0));
		assertTrue(pa.accept(4, 50.0));
		// fin qui ho inserito 10,20,30,40,50
		assertEquals(pa.aggregateAll(),new Double(150.0)); // 10+20+30+40+50
		assertTrue(pa.accept(2, 31.0));
		// avendo reinserito a metà, ora ho 10,20,31
		assertEquals(pa.aggregateAll(),new Double(61.0)); // 10+20+31
		assertTrue(pa.accept(3, 41.0));
		assertFalse(pa.accept(4, 30.0)); // non accettato, non è maggiore del precedente
		assertFalse(pa.accept(5, 50.0)); // non accettato, è fuori ordine
		assertTrue(pa.accept(4, 51.0));
		// fin qui ho inserito 10,20,31,41,51
		assertEquals(pa.aggregateAll(),new Double(153.0));
		assertTrue(pa.accept(0, 0.0));
		// avendo reinserito all'inizio, ora ho solo lo 0
		assertEquals(pa.aggregateAll(),new Double(0.0));
	}
	
	@org.junit.Test
	public void testAnotherOK() {
		ProgressiveAcceptor<String> pa = new ProgressiveAcceptorImpl<>();
		// aggrego concatenando le stringhe con una virgola in mezzo
		pa.setAggregator(new Aggregator<String>(){

			@Override
			public String aggregate(String one, String two) {
				return one + "," + two;
			}
		});
		// accetto solo stringhe non vuote (non c'è correlazione con la stringa previous)
		pa.setProgressiveFilter(new ProgressiveFilter<String>(){

			@Override
			public boolean isNextOK(String previous, String next) {
				return next.length()>0;
			}
		});
		pa.setSize(5);
		assertTrue(pa.accept(0, "ciao"));
		assertTrue(pa.accept(1, "ciao"));
		assertFalse(pa.accept(2, ""));
		assertEquals(pa.aggregateAll(),"ciao,ciao");
	}
	
	@org.junit.Test
	public void testExceptions() {
		ProgressiveAcceptor<Double> pa = new ProgressiveAcceptorImpl<>();
		// non posso specificare un aggregatore null! (stessa cosa per il filtro)
		try{
			pa.setAggregator(null);
		} catch (NullPointerException e){
		} catch (Exception e){ // questa catch intercetta il caso di eccezioni lanciate ma del tipo sbagliato
			fail("Wrong exception thrown");
		}
		pa.setAggregator(new Aggregator<Double>(){

			@Override
			public Double aggregate(Double one, Double two) {
				return one+two;
			}
			
		});
		// non posso cominciare ad accettare se non ho il filtro e la size
		try{
			pa.accept(0, 10.0);
			fail("Not correctly initialized");
		} catch (IllegalStateException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
		pa.setProgressiveFilter(new ProgressiveFilter<Double>(){

			@Override
			public boolean isNextOK(Double previous, Double next) {
				return next > previous;
			}
			
		});
		try{
			pa.setSize(-1);
			fail("Not negative!");
		} catch (IllegalArgumentException e){
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
}
