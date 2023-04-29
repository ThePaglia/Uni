package ex2014.a05.sol2;

import java.util.*;
import java.util.stream.Collectors;

public class RouletteImpl implements Roulette{
	
	private boolean betting = false;
	private final Map<String,List<Pair<Integer,Integer>>> iBets = new HashMap<>();
	private int lastWin;
	private final Random rnd = new Random(); 

	@Override
	public void waitBets() {
		this.betting = true;
		this.iBets.clear();
	}
	
	@Override
	public void bet(String playerName, int betNumber, int quantity) {
		if (!this.betting){
			throw new IllegalStateException();
		}
		if (!this.iBets.containsKey(playerName)){
			this.iBets.put(playerName,new ArrayList<>());
		}
		this.iBets.get(playerName).add(new Pair<>(betNumber,quantity));
	}

	@Override
	public int drawWinningNumber() {
		int d = this.rnd.nextInt(37);
		this.debugDrawing(d);
		return d;
	}
	
	@Override
	public void debugDrawing(int drawnNumber) {
		if (!this.betting){
			throw new IllegalStateException();
		}
		this.betting = false;
		this.lastWin = drawnNumber;
	}

	@Override
	public Map<String, Integer> getWins() {
		return this.iBets.entrySet().stream()
				                    .collect(Collectors.toMap(
				                    		(x)->x.getKey(),
				                    		(x)->x.getValue().stream()
				                    		                 .filter((p)->p.getX()==this.lastWin)
				                    		                 .mapToInt(Pair::getY)
				                    		                 .sum()*SINGLE_BET_WIN_FACTOR
				                    ));
	}

		@Override
	public int getTotalBets() {
		return this.iBets.values().stream()
				                  .mapToInt((l)->l.stream()
				                		          .mapToInt(Pair::getY)
				                		          .sum())
				                  .sum();
	}

}
