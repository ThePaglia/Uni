package a02a.sol2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
	
	private int selection = 0;
	private List<Integer> list;
	
	public LogicsImpl(int size) {
		final Random r = new Random();
		this.list = new LinkedList<>(IntStream.range(0,size).map(i -> r.nextInt(10)).boxed().collect(Collectors.toList()));
	}
	

	@Override
	public void select(int position) {
		this.selection = position;
	}

	@Override
	public int getSelected() {
		return this.selection;
	}

	@Override
	public List<Integer> getNumbers() {
		return Collections.unmodifiableList(this.list);
	}

	@Override
	public boolean isDone() {
		int prev = -1;
		for (int i: this.list) {
			if (prev > i) {
				return false;
			}
			prev = i;
		}
		return true;
	}


	@Override
	public void left() {
		if (this.selection > 0) {
			Collections.swap(this.list, this.selection, this.selection-1);
			this.selection = this.selection-1;
		}
	}

	@Override
	public void right() {
		if (this.selection < this.list.size()-1) {
			Collections.swap(this.list, this.selection, this.selection+1);
			this.selection = this.selection+1;
		}
	}

}
