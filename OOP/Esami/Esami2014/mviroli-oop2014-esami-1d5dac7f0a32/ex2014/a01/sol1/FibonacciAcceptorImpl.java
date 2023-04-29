package ex2014.a01.sol1;

import java.util.*;
import java.util.stream.*;

/**
 * Qualche nota su questa soluzione
 * - è solo una delle possibili
 * - usa alcune delle funzionalità di Java 8, come gli Optional e gli Stream, ma questo non era necessario
 * - può essere considerata come soluzione allo "stato dell'arte", e quindi approfondibile dallo studente  
 */

public class FibonacciAcceptorImpl implements FibonacciAcceptor {
	
	private final Map<String,List<Long>> map = new HashMap<>();
	private Optional<List<Long>> currentSequence = Optional.empty(); 
	
	@Override
	public void reset(final String sequenceName) {
		if (map.containsKey(sequenceName)){
			throw new IllegalArgumentException();
		}
		this.currentSequence = Optional.of(new LinkedList<>()); 
		map.put(sequenceName, this.currentSequence.get());
	}

	@Override
	public boolean consumeNext(final long l) {
		if (this.currentSequence.orElseThrow(()->new IllegalStateException()).size()<2 || 
			l == this.currentSequence.get().get(this.currentSequence.get().size()-1) +
				 this.currentSequence.get().get(this.currentSequence.get().size()-2)){
			this.currentSequence.get().add(l);
			return true;
		}
		return false;
	}

	@Override
	public List<Long> getCurrentSequence() {
		return defend(this.currentSequence.orElseThrow(()->new IllegalStateException()));
	}

	@Override
	public Map<String, List<Long>> getAllSequences() {
		// note we defend the map but also each list in it
		return this.map.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->defend(e.getValue())));
	}
	
	// Local implementation of "defensive copy" for a List<X>
	private static <X> List<X> defend(List<X> list){
		return new LinkedList<>(list);
	}

}
