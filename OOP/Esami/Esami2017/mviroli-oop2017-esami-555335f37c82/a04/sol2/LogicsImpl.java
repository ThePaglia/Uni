package a04.sol2;

import java.util.*;
import java.util.stream.*;

public class LogicsImpl implements Logics {
	
	private List<Integer> list = null;
	private Random r = new Random();
	
	public LogicsImpl() {
		this.list = Stream.generate(()->drawOne()).limit(5).collect(Collectors.toCollection(ArrayList::new));
	}
	
	private int drawOne() {
		return r.nextInt(6)+1;
	}
	
	@Override
	public void redraw(List<Boolean> b) {
		for (int i=0; i<5; i++) {
			if (b.get(i)) {
				this.list.set(i,drawOne());
			}
		}
	}

	@Override
	public List<Integer> getDices() {
		return Collections.unmodifiableList(this.list);
	}

	@Override
	public Result getResult() {
		Map<Integer,Integer> map = this.list.stream().collect(Collectors.toMap(x->x,x->1,(x,y)->x+y));
		if (map.size() == 5 && (!map.containsKey(1) || !map.containsKey(6))) { 
			return Result.STRAIGHT;
		} else if (map.size() == 3 && map.containsValue(3)) {
			return Result.THREE;
		} else if (map.size() == 2 && map.containsValue(3)) {
			return Result.FULL;
		} else if (map.size() == 2) {
			return Result.FOUR;
		} else if (map.size() == 1) {
			return Result.YAHTZEE;
		} else {
			return Result.NOTHING;
		}
	}
	
}
