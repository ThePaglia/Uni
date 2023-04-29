package ex2014.a04.sol1;

import java.util.*;
import java.util.stream.Collectors;
import static ex2014.a04.sol1.TicTacToe.Cell.*;

/**
 * Questa interfaccia è usata per controllare una partita del gioco TicTacToe (tris).
 * In generale, richiamare un metodo quando non appropriato (ad esempio, muovere quando la partita è finita) deve lanciare un IllegalStateException.
 * Anche NullPointerException eIllegalArgumentException vadano usati ove necessario per rendere l'implementazione completa e robusta. 
 */

public class TicTacToeImpl implements TicTacToe {
	
	private Optional<Player> turn = Optional.empty();
	private Set<Cell> available = new HashSet<>(Arrays.asList(Cell.values()));
	private Map<Player,Set<Cell>> map = new HashMap<>();
	private Optional<Player> won = Optional.empty();
	
	public TicTacToeImpl(){
		this.map.put(Player.X,new HashSet<>());
		this.map.put(Player.O,new HashSet<>());
	}
	
	@Override
	public void start(Player p) {
		if (turn.isPresent() || this.isOver()){
			throw new IllegalStateException();
		}
		this.turn = Optional.of(p);
	}
	
	private Player otherTurn(Player p){
		return p==Player.X ? Player.O : Player.X;
	}
	

	@Override
	public Player nextPlayer() {
	    return this.turn.isPresent() || this.isOver() ? null : otherTurn(this.turn.get()); 	
	}

	@Override
	public boolean isOver() {
		return this.available.size()==0 || this.won.isPresent();
	}

	@Override
	public Player won(){
		return this.won.orElse(null);
	}

	@Override
	public boolean isEven() {
		return this.available.size()==0 && !this.won.isPresent();
	}

	@Override
	public void move(Cell c, Player p) {
		if (!this.turn.isPresent() || this.turn.get()!=p || !this.available.contains(c)){
			throw new IllegalStateException();
		}
		this.available.remove(c);
		this.map.get(p).add(c);
		if (WINNING_TRIPLES.stream().anyMatch((l)->this.map.get(p).containsAll(l))){
			this.won = Optional.of(p);
		} 
		this.turn = Optional.of(otherTurn(p));
	}

	@Override
	public Set<Cell> getAvailableCells() {
		return new HashSet<>(this.available);
	}
	
	@Override
	public Cell getAvailableCell(int row, int col) {
		return this.getAvailableCells().stream().filter((c)->c.getRow()==row && c.getColumn()==col).findAny().orElse(null);
	}

	@Override
	public Map<Player, Set<Cell>> getOccupiedCells() {
		return this.map.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->defend(e.getValue())));
	}
	
	// Local implementation of "defensive copy" for a Set<X>
	private static <X> Set<X> defend(Set<X> set){
		return new HashSet<>(set);
	}

}
