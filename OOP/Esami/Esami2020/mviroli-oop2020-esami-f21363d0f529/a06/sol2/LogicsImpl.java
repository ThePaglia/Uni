package a06.sol2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LogicsImpl implements Logics {
	
	private final List<Integer> numbers = new ArrayList<>();
	private int item = 0;
	private final Random random = new Random();
	
	public LogicsImpl(int width) {
		for (int i=0; i<width; i++) {
			numbers.add(0);
		}
		while (numbers.stream().filter(i->i>0).count() < 3) {
			numbers.set(random.nextInt(width-1)+1, random.nextInt(5)+1);
		} 
	}	

	@Override
	public List<Integer> getNumbers() {
		return Collections.unmodifiableList(numbers);
	}

	@Override
	public int getItemPosition() {
		return this.item;
	}
	
	@Override
	public void next() {
		this.item++;
		if (this.numbers.get(this.item)>0) {
			this.numbers.set(this.item,this.numbers.get(this.item)-1);
		}
		if (this.numbers.get(this.item)>0) {
			this.item = 0;
		}
	}
	
	@Override
	public boolean isOver() {
		return numbers.stream().allMatch(i->i==0);
	}
}
