package a01a.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {

	private final Map<Pair<Integer, Integer>, Player> cells = new HashMap<>();
	private final int size;
	private Player turn = Player.O;
	private Optional<Player> winner = Optional.empty();

	public LogicsImpl(int size) {
		this.size = size;
	}

	@Override
	public Player next() {
		return this.turn;
	}

	@Override
	public boolean hit(int x, int y) {
		var p = new Pair<>(x,y);
		if (!this.winner.isPresent() && !cells.containsKey(p) && (y==size-1 || cells.containsKey(new Pair<>(x,y+1)))){
			cells.put(p,this.turn);
			if (this.isWinning(x, y,this.turn)) {
				this.winner = Optional.of(this.turn);
			}
			this.turn = this.turn.other();
			return true;
		}
		return false;
	}
	
	private boolean checkCell(int x, int y, Player p) {
		return this.cells.get(new Pair<>(x,y)) == p;
	}
	
	private boolean isWinning(int x, int y, Player p) {
		return (this.checkCell(x-1,y,p) && this.checkCell(x+1,y,p)) ||
				(this.checkCell(x-1,y,p) && this.checkCell(x-2,y,p)) ||
				(this.checkCell(x+1,y,p) && this.checkCell(x+2,y,p)) ||
				(this.checkCell(x,y+1,p) && this.checkCell(x,y+2,p));	
	}

	@Override
	public boolean isOver() {
		return this.winner.isPresent() || this.cells.size() == size*size;
	}

}
