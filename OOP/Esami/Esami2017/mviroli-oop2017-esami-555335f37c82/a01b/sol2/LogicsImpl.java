package a01b.sol2;

import java.util.*;
import java.util.stream.*;

/*
 * Si noti che questo Logics è più generale del necessario, gestendo ad esempio anche l'abilitazione dei pulsanti
 * Lo studente noti però l'uso notevole della enumerazione
 */

public class LogicsImpl implements Logics {
	
	private static final int DROPPED = -1; 
	
	private Random rnd = new Random();
	private List<Integer> list = new ArrayList<>(); 
	private List<Integer> orderedList = null;

	@Override
	public void reset(int size,int max) {
		IntStream.range(0, size).forEach((i)->{
			list.add(this.rnd.nextInt(max));
		});
		this.orderedList = new ArrayList<>(this.list); 
		this.orderedList.sort((x,y)->x-y);
	}

	@Override
	public Optional<Integer> tryNumber(int elem) {
		int n = this.list.get(elem);
		if (this.orderedList.get(0) == n){
			this.list.set(elem,DROPPED);
			this.orderedList.remove((Object)n);
			return Optional.of(n);
		} 
		return Optional.empty();
	}	
	
}

