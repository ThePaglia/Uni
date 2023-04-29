package ex2014.a01b.sol1;

/**
 * Questa interfaccia è usata per controllare una GUI, GUI che chiede ad un utente una sequenza di elementi del tipo X, 
 * con la possibilità di rifiutare un elemento dipendentemente da quale fosse il precedente (attraverso un filtro), 
 * di aggregare tutti gli elementi finora prodotti in un unico valore X, e di re-inserire la sequenza da un punto 
 * precedente (ad esempio dall'inizio, o da metà).
 * 
 * Questa interfaccia dipende da altre due interfacce, ProgressiveFilter and Aggregator (che realizzano strategie funzionali).
 * 
 * Un filtro, un aggregatore e la size sono necessarie, altrimenti un IllegalStateException sarà lanciata dagli altri metodi.
 */

public interface ProgressiveAcceptor<X> {
	
	
	/**
	 * @param filter, the filter this acceptor will use
	 */
	void setProgressiveFilter(ProgressiveFilter<X> filter);
	
	/**
	 * @param aggregator, the aggregator this acceptor will use
	 */
	void setAggregator(Aggregator<X> aggregator);

	/**
	 * @param size, the maximum size of the sequence to accept
	 */
	void setSize(int size);
	
	/**
	 * This method accepts a new element of the sequence. An element is accepted if it is OK with respect to previous one 
	 * according to the filter. Ideally elements are accepted in the order of position 0,1,2,3..
	 * If one re-accepts a previous element, the subsequent part of the sequence is erased and needs to be re-accepted.
	 * For instance, if the sequence accepts 10,20,30,40 in positions 0,1,2,3, and then one re-accepts 21 in position 1,
	 * the new sequence is 10,21 (30 and 40 are erased). 
	 * 
	 * @param pos, the position of the element
	 * @param elem, the element
	 * @return true if the element is accepted
	 */
	boolean accept(int pos, X elem);
	
	/**
	 * @return the single value that the current sequence aggregates to
	 */
	X aggregateAll();

}
