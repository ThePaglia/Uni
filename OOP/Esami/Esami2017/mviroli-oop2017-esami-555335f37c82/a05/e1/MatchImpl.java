package a05.e1;

public class MatchImpl implements Match {
	
	private final String homeTeam;
	private final String awayTeam;
	
	public MatchImpl(String homeTeam, String awayTeam) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}

	@Override
	public String getHomeTeam() {
		return this.homeTeam;
	}

	@Override
	public String getAwayTeam() {
		return this.awayTeam;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((awayTeam == null) ? 0 : awayTeam.hashCode());
		result = prime * result + ((homeTeam == null) ? 0 : homeTeam.hashCode());
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
		if (awayTeam == null) {
			if (other.awayTeam != null) {
				return false;
			}
		} else if (!awayTeam.equals(other.awayTeam)) {
			return false;
		}
		if (homeTeam == null) {
			if (other.homeTeam != null) {
				return false;
			}
		} else if (!homeTeam.equals(other.homeTeam)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "M[" + homeTeam + "," + awayTeam + "]";
	}
	
	

}
