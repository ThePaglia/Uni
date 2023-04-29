package ex2014.a04.sol1_java7;

import java.util.*;

/**
 * Questa interfaccia è usata per controllare una partita del gioco TicTacToe (tris).
 * In generale, richiamare un metodo quando non appropriato (ad esempio, muovere quando la partita è finita) deve lanciare un IllegalStateException.
 * Non è necessario invece intercettare e gestire il NullPointerException. 
 * Attenzione a creare copie difensive degli oggetti prodotti in uscita da questa classe.
 */

public interface TicTacToe {
	
	/*
	 * An enumeration modelling the two players
	 */
	
	enum Player { X, O };
	
	/*
	 * An enumeration modelling the 9 cells of the game.
	 * They are called NW (north-west), N (north), NE (north-east), W (west), C (center),...
	 * Each cell has a field row (0,1,2) and col (0,1,2), accessible via getter methods
	 * A constant WINNING_TRIPLES indicates all possible winning triples. 
	 * Check documentation of java.lang.Enum to obtain useful methods for enumerations.
	 */
	
	enum Cell {
		
		NW(0,0), N(0,1), NE(0,2),
		W(1,0), C(1,1), E(1,2),
		SW(2,0), S(2,1), SE(2,2);
		
		public static final List<List<Cell>> WINNING_TRIPLES = Arrays.asList(
				Arrays.asList(NE,N,NW), Arrays.asList(E,C,W), Arrays.asList(SE,S,SW),
				Arrays.asList(NE,E,SE), Arrays.asList(N,C,S), Arrays.asList(NW,W,SW),
				Arrays.asList(NE,C,SW), Arrays.asList(NW,C,SE)
		);
		
		final private int row;
		final private int col;
		
		private Cell(int row,int col){
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public int getColumn() {
			return this.col;
		}
		
	}
	
	/**
	 * Starts a game (can be called only once) 
	 * 
	 * @param p is the first player to "move"
	 */
	void start(Player p);
	
	/**
	 * @return the next player to "move", or null if none exist
	 */
	Player nextPlayer();
	
	/**
	 * @return whether the game is even (Italian: "pari")
	 */
	boolean isEven();

	/**
	 * @return whether the game is over (someone won or it's an even game)
	 */
	boolean isOver();
	
	/**
	 * @return the player who won the game, or null if none exist
	 */
	Player won();
		
	/**
	 * makes a move
	 * 
	 * @param c is the cell where a new sign is provided
	 * @param p is the player who moves
	 */
	void move(Cell c,Player p);
	
	/**
	 * @return the set of all cells where next player can move
	 */
	Set<Cell> getAvailableCells();
	
	/**
	 * @param row (0,1,2)
	 * @param col (0,1,2)
	 *
	 * @return the cell at position row and col, or null if it is not available
	 */
	Cell getAvailableCell(int row,int col);
	
	/**
	 * @return a map associating each player to set of cells it already occupied
	 */
	Map<Player,Set<Cell>> getOccupiedCells();
}
