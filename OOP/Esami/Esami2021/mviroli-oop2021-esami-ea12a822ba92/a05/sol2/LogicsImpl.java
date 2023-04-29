package a05.sol2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Deque;
import java.util.Random;
import java.util.function.IntBinaryOperator;

public class LogicsImpl implements Logics {
	
	private final Map<Pair<Integer,Integer>, Object> map = new HashMap<>();
	private boolean status = true;
	private Optional<Operation> pendingOperation = Optional.of(Operation.OR);
	private Deque<Pair<Integer,Integer>> selected = new LinkedList<>();
	
	public LogicsImpl(int size) {
		final Random random = new Random();
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				Object c = (i+j)%2 == 0 
						? random.nextBoolean()
						: Operation.values()[random.nextInt(Operation.values().length)];
				map.put(new Pair<>(i,j), c);
			}
		}
	}
	
	private Object get(int x, int y) {
		return this.map.get(new Pair<>(x,y));
	}

	@Override
	public boolean isCellValue(int x, int y) {
		return !(this.get(x,y) instanceof Operation);
	}

	@Override
	public Optional<Operation> getCellAsOperation(int x, int y) {
		return Optional.of(this.get(x,y)).filter(o -> o instanceof Operation).map(o -> (Operation)o);
	}

	@Override
	public Optional<Boolean> getCellAsValue(int x, int y) {
		return Optional.of(this.get(x,y)).filter(o -> o instanceof Boolean).map(o -> (Boolean)o);
	}

	@Override
	public boolean hit(int x, int y) {
		var position = new Pair<>(x,y);
		if (!this.selected.isEmpty() && !this.isAdjacent(this.selected.getLast(),new Pair<>(x,y))) {
			return false;
		}
		if (this.isCellValue(x,y) && this.pendingOperation.isPresent()) {
			this.status =this.pendingOperation.get().getOperator().apply(status, this.getCellAsValue(x, y).get());
			this.pendingOperation = Optional.empty();
			this.selected.addLast(position);
			return true;
		} else if (!this.isCellValue(x,y) && this.pendingOperation.isEmpty()) {
			this.pendingOperation = Optional.of(this.getCellAsOperation(x, y).get());
			this.selected.addLast(position);
			return true;
		}
		return false;
	}

	private boolean isAdjacent(Pair<Integer, Integer> last, Pair<Integer, Integer> other) {
		return Math.abs(last.getX() - other.getX()) + Math.abs(last.getY() - other.getY()) == 1;  
	}

	@Override
	public Optional<Boolean> computeResult() {
		return Optional.of(this.status).filter(i -> this.pendingOperation.isEmpty());
	}
}
