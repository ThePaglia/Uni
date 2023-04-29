package ex2014.a04.sol2_java7;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class ControllerImpl implements Controller {
	
	private final Random rnd = new Random();
	/*
	 * Must be a Deque and a List, hence I'm not using interfaces
	 */
	private LinkedList<int[]> list; 

	@Override
	public void reset(final int size, final int max) {
		list = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			list.add(new int[]{i, rnd.nextInt(max)});
		}
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(final int[] o1, final int[] o2) {
				return o2[1] - o1[1];
			}
		});
	}

	@Override
	public Integer tryNumber(final int elem) {
		if (list.peek()[0] == elem){
			return list.pop()[1];
		} 
		return null;
	}

	@Override
	public boolean allFound() {
		return list.isEmpty();
	}
	
	
}

