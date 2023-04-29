package a04.sol1;

/**
 * A standard implementation of a match, with equals/hashcode based on both players
 */
class MatchImpl implements Match {
	
	private Player firstPlayer;
	private Player secondPlayer;

	public MatchImpl(Player firstPlayer, Player secondPlayer) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
	}
	
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	
	public Player getSecondPlayer() {
		return secondPlayer;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstPlayer == null) ? 0 : firstPlayer.hashCode());
		result = prime * result + ((secondPlayer == null) ? 0 : secondPlayer.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MatchImpl)) {
			return false;
		}
		MatchImpl other = (MatchImpl) obj;
		if (firstPlayer == null) {
			if (other.firstPlayer != null) {
				return false;
			}
		} else if (!firstPlayer.equals(other.firstPlayer)) {
			return false;
		}
		if (secondPlayer == null) {
			if (other.secondPlayer != null) {
				return false;
			}
		} else if (!secondPlayer.equals(other.secondPlayer)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "M[" + firstPlayer + "," + secondPlayer + "]";
	}
}
