package a05.sol1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ChampionshipImpl implements Championship {
	
	private enum Result {
		HOMEWIN, HOMELOSS, EVEN;
		
		public int points(Match m, String team) {
			if (team.equals(m.getHomeTeam())) {
				return this == HOMEWIN ? 3 : this == EVEN ? 1: 0;
			} else if (team.equals(m.getAwayTeam())) {
				return this == HOMEWIN ? 0 : this == EVEN ? 1: 3;				
			}
			return 0;
		}
		
		public static Result fromGoals(int homeGoals, int awayGoals) {
			return homeGoals > awayGoals ? HOMEWIN : homeGoals == awayGoals ? EVEN : HOMELOSS;
		}
	}
	private final List<String> teams = new ArrayList<>();
	private Map<Match, Optional<Result>> matches;

	@Override
	public void registerTeam(String name) {
		this.teams.add(name);
	}

	@Override
	public void startChampionship() {
		this.matches = new HashMap<>();
	}

	@Override
	public Set<Match> pendingMatches() {
		return this.matches.entrySet()
				   .stream()
		           .filter(e -> !e.getValue().isPresent())
		           .map(e -> e.getKey())
		           .collect(Collectors.toSet());
	}

	@Override
	public void matchPlay(Match m, int homeGoals, int awayGoals) {
		this.matches.put(m, Optional.of(Result.fromGoals(homeGoals, awayGoals)));
	}
	
	private boolean teamIncluded(String team, Match match) {
		return team.equals(match.getAwayTeam()) || team.equals(match.getHomeTeam());
	}
	
	private boolean notScheduled(String team) {
		return !this.pendingMatches().stream()
						   .filter(m -> teamIncluded(team,m))
				           .anyMatch(e -> true);
	}

	@Override
	public void newDay() {
		for (String team: this.teams) {
			if (notScheduled(team)) {
				for (String team2: this.teams) {
					Match match = new MatchImpl(team,team2);
					if (!team.equals(team2) && notScheduled(team2) && !matches.containsKey(match)) {
						this.matches.put(match, Optional.empty());
						break;
					}
				}
			}
		}
	}

	@Override
	public Map<String, Integer> getClassification() {
		final Map<String, Integer> classification = this.teams.stream().collect(Collectors.toMap(team->team, team->0));
		for (Entry<Match,Optional<Result>> e: matches.entrySet()) {
			if (e.getValue().isPresent()) {
				int homePoints = e.getValue().get().points(e.getKey(), e.getKey().getHomeTeam());
				int awayPoints = e.getValue().get().points(e.getKey(), e.getKey().getAwayTeam());
				classification.merge(e.getKey().getHomeTeam(), 0, (i,j)->i+homePoints);
				classification.merge(e.getKey().getAwayTeam(), 0, (i,j)->i+awayPoints);
			}
		}
		return classification;
	}

	@Override
	public boolean championshipOver() {
		return this.pendingMatches().isEmpty() && this.matches.size()==this.teams.size()*(this.teams.size()-1);
	}

}
