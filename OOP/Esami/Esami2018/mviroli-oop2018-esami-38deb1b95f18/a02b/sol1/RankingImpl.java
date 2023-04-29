package a02b.sol1;

import java.util.*;
import java.util.stream.Collectors;

public class RankingImpl implements Ranking {
	
	private final List<Tournament> tournaments = new LinkedList<>();
	
	// Comparatore per tornei come costante
	private static final Comparator<Tournament> TC = 
			(t1,t2)-> t1.getYear()==t2.getYear() ? t1.getWeek()-t2.getWeek() 
					                             : t1.getYear()-t2.getYear();
	
	// controllo che un torneo sia nell'anno in corso		
	private boolean inCurrentYear(Tournament t) {
		this.checkNonEmptyTournaments();
		return t.getYear()==this.tournaments.get(0).getYear() || (
				t.getYear()==this.tournaments.get(0).getYear()-1 &&
				t.getWeek()>this.tournaments.get(0).getWeek());
	}		
			
	private void checkNonEmptyTournaments() {
		if (this.tournaments.isEmpty()) {
			throw new IllegalStateException();
		}
	}
	@Override
	public void loadTournament(Tournament t) {
		if (!this.tournaments.isEmpty() && TC.compare(this.tournaments.get(0),t)>=0) {
			throw new IllegalStateException();
		}
		this.tournaments.add(0,t);
	}

	@Override
	public int getCurrentWeek() {
		this.checkNonEmptyTournaments();
		return this.tournaments.get(0).getWeek();
	}

	@Override
	public int getCurrentYear() {
		this.checkNonEmptyTournaments();
		return this.tournaments.get(0).getYear();
	}

	@Override
	public Integer pointsFromPlayer(String player) {
		return this.tournaments.stream()
				   .filter(t -> inCurrentYear(t))
				   .map(t -> t.getResult(player))
				   .filter(Optional::isPresent)
				   .map(Optional::get)
				   .reduce(0, (x,y)->x+y);
	}

	@Override
	public List<String> ranking() {
		return this.tournaments.stream()
				               .flatMap(t->t.getPlayers().stream())
				               .distinct()
				               .map(p -> new Pair<>(p,pointsFromPlayer(p)))
				               .sorted((p1,p2)->p2.getY()-p1.getY())
				               .map(Pair::getX)
				               .collect(Collectors.toList());
	}

	@Override
	public Map<String, String> winnersFromTournamentInLastYear() {
		return this.tournaments.stream()
				               .filter(t -> inCurrentYear(t))
				               .collect(Collectors.toMap(t->t.getName(), t->t.winner()));
	}

	@Override
	public Map<String, Integer> pointsAtEachTournamentFromPlayer(String player) {
		return this.tournaments.stream()
	               .filter(t -> t.getPlayers().contains(player))
	               .collect(Collectors.toMap(t->t.getName(), t->t.getResult(player).get()));
	}

	@Override
	public List<Pair<String, Integer>> pointsAtEachTournamentFromPlayerSorted(String player) {
		return this.tournaments.stream()
	               .filter(t -> t.getPlayers().contains(player))
	               .sorted(TC)
	               .map(t -> new Pair<>(t.getName(),t.getResult(player).get()))
	               .collect(Collectors.toList());
	}

}
