package a04.sol1;

/**
 * A standard implementation of a player, with equals/hashcode based on id 
 */
class PlayerImpl implements Player {
	
	private final int id;
	
	private final String name;
	
	public PlayerImpl(int id, String name){
		this.id=id;
		this.name=name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (!(obj instanceof PlayerImpl)) {
			return false;
		}
		PlayerImpl other = (PlayerImpl) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
