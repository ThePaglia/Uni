package ex2014.a04.sol1_java7;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ex2014.a04.sol1_java7.TicTacToe.Cell.*;

/**
 * Questa interfaccia è usata per controllare una partita del gioco TicTacToe
 * (tris). In generale, richiamare un metodo quando non appropriato (ad esempio,
 * muovere quando la partita è finita) deve lanciare un IllegalStateException.
 * Anche NullPointerException eIllegalArgumentException vadano usati ove
 * necessario per rendere l'implementazione completa e robusta.
 */

public class TicTacToeImpl implements TicTacToe {
	
	private final Set<Cell> available = new HashSet<>(Arrays.asList(Cell.values()));
	private final Map<Player,Set<Cell>> map = new HashMap<>();
	private Player turn;
	private Player won;
	
	public TicTacToeImpl(){
		map.put(Player.X, new HashSet<Cell>());
		map.put(Player.O, new HashSet<Cell>());
	}
	
	@Override
	public void start(final Player p) {
		if (turn != null || isOver()){
			throw new IllegalStateException();
		}
		turn = p;
	}
	
	private Player otherTurn(final Player p){
		return p==Player.X ? Player.O : Player.X;
	}
	

	@Override
	public Player nextPlayer() {
	    return turn != null || isOver() ? null : otherTurn(turn); 	
	}

	@Override
	public boolean isOver() {
		return available.size()==0 || won != null;
	}

	@Override
	public Player won(){
		return won;
	}

	@Override
	public boolean isEven() {
		return available.size()==0 && won == null;
	}

	private static boolean isVictorious(final Set<Cell> v) {
		for (List<Cell> l: WINNING_TRIPLES) {
			if (v.containsAll(l)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void move(final Cell c, final Player p) {
		if (turn == null || turn != p || !available.contains(c)){
			throw new IllegalStateException();
		}
		available.remove(c);
		final Set<Cell> v = map.get(p);
		v.add(c);
		if (isVictorious(v)){
			won = p;
		} 
		turn = otherTurn(p);
	}

	@Override
	public Set<Cell> getAvailableCells() {
		return Collections.unmodifiableSet(available);
	}
	
	@Override
	public Cell getAvailableCell(final int row, final int col) {
		for (Cell c: available) {
			if (c.getRow() == row && c.getColumn() == col) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Map<Player, Set<Cell>> getOccupiedCells() {
		final Map<Player, Set<Cell>> res = new HashMap<>(map.size());
		for (Map.Entry<Player, Set<Cell>> e: map.entrySet()) {
			res.put(e.getKey(), Collections.unmodifiableSet(e.getValue()));
		}
		return res;
	}
	
}
