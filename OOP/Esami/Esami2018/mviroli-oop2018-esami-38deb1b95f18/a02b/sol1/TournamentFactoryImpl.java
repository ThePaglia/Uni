package a02b.sol1;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class TournamentFactoryImpl implements TournamentFactory {

	@Override
	public Tournament make(String name, int year, int week, Set<String> players, Map<String, Integer> points) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(players);
		Objects.requireNonNull(points);
		
		return new Tournament() {
			
			@Override
			public String getName() {
				return name;
			}

			@Override
			public int getYear() {
				return year;
			}

			@Override
			public int getWeek() {
				return week;
			}

			@Override
			public Set<String> getPlayers() {
				return Collections.unmodifiableSet(players);
			}

			@Override
			public Optional<Integer> getResult(String player) {
				return Optional.of(player)
							   .filter(p -> players.contains(p))
						       .map(p -> points.containsKey(p) ? points.get(p) : 0);
			}

			@Override
			public String winner() {
				return players.stream().max((p1,p2)->getResult(p1).get()-getResult(p2).get()).get();
			}
			
		};
	}
}
