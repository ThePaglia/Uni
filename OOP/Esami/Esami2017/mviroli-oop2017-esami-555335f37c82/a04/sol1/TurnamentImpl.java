package a04.sol1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TurnamentImpl implements Turnament {

	private final static List<Integer> SIZES = Arrays.asList(1, 2, 4, 8, 16, 32, 64);

	private final List<Player> players = new ArrayList<>();
	private final List<Player> surviving = new ArrayList<>();
	private final Map<Match, Optional<Player>> matches = new HashMap<>();

	private void require(boolean b, String msg) {
		if (!b) {
			throw new IllegalArgumentException(msg);
		}
	}

	@Override
	public Player makePlayer(int id, String name) {
		return new PlayerImpl(id, name);
	}

	@Override
	public Match makeMatch(Player p1, Player p2) {
		return new MatchImpl(p1, p2);
	}

	@Override
	public void registerPlayer(Player player) {
		require(this.matches.isEmpty(), "can't register if turnament is already started");
		require(!players.contains(player), "player already registered");
		players.add(player);
	}

	@Override
	public void startTurnament() {
		require(SIZES.contains(players.size()), "turnament incomplete");
		require(matches.isEmpty(), "turnament already started");
		this.surviving.addAll(this.players);
		this.addNewMatches();
	}

	private void addNewMatches() {
		if (matches.isEmpty() || (!matches.containsValue(Optional.empty()) && surviving.size() > 1)) {
			for (int i = 0; i < surviving.size(); i = i+2) {
				this.matches.put(new MatchImpl(surviving.get(i), surviving.get(i + 1)), Optional.empty());
			}
		}
	}

	@Override
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	private boolean hasPlayer(Match match, Player player) {
		return match.getFirstPlayer() == player || match.getSecondPlayer() == player;
	}

	private Player otherPlayer(Match match, Player player) {
		return match.getFirstPlayer() == player ? match.getSecondPlayer() : match.getFirstPlayer();
	}

	@Override
	public List<Match> getPendingGames() {
		return this.matches
				   .entrySet()
				   .stream()
				   .filter(e -> !e.getValue().isPresent())
				   .map(e -> e.getKey())
				   .collect(Collectors.toList());
	}

	@Override
	public void playMatch(Match match, Player winner) {
		require(matches.containsKey(match), "match non pending");
		Player loser = otherPlayer(match, winner);
		surviving.remove(loser);
		matches.put(match, Optional.of(winner));
		this.addNewMatches();
	}

	@Override
	public boolean isTurnamentOver() {
		return surviving.size() == 1;
	}

	@Override
	public Player winner() {
		require(isTurnamentOver(), "turnament has no winner yet");
		return surviving.get(0);
	}

	@Override
	public Set<Player> opponents(Player player) {
		return matches.keySet()
				      .stream()
				      .filter(m -> hasPlayer(m, player))
				      .map(m -> otherPlayer(m, player))
				      .collect(Collectors.toSet());
	}

}
