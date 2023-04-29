package a02a.sol1;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import a02a.sol1.Tournament.Result;
import a02a.sol1.Tournament.Type;

public class TournamentBuilderImpl implements TournamentBuilder {
	
	// codifico l'associazione torneo-punti in una costante, mediante l'inizializzatore non-statico
	private static final EnumMap<Type,Integer> TYPE_POINTS = new EnumMap<Type,Integer>(Type.class){{
		this.put(Type.MAJOR, 2500);
		this.put(Type.ATP1000, 1000);
		this.put(Type.ATP500, 500);
		this.put(Type.ATP250, 250);
	}};
	
	// codifico l'associazione risultato-percentuale di punti in una costante, mediante l'inizializzatore non-statico
	private static final EnumMap<Result,Double> RESULT_FACTOR = new EnumMap<Result,Double>(Result.class){{
		this.put(Result.WINNER, 1.0);
		this.put(Result.FINALIST, 0.5);
		this.put(Result.SEMIFINALIST, 0.2);
		this.put(Result.QUARTERFINALIST, 0.1);
		this.put(Result.PARTICIPANT, 0.0);
	}};
	
	
	private Optional<Type> optType = Optional.empty();
	private Optional<String> optName = Optional.empty();
	private Optional<Map<String,Integer>> optInitialRanking = Optional.empty();
	private Optional<Map<String,Integer>> optFinalRanking = Optional.empty();
	private final Map<String,Result> results = new HashMap<>();
	private boolean built = false;
	
	private static void check(boolean b) {
		if (!b) {
			throw new IllegalStateException();
		}
		
	}
	
	private static <X> Optional<X> reassignOptional(Optional<X> opt, X x) {
		check(!opt.isPresent());
		return Optional.of(x);
	}

	@Override
	public TournamentBuilder setType(Type type) {
		check(!this.built);
		this.optType = reassignOptional(this.optType,type);
		return this;
	}

	@Override
	public TournamentBuilder setName(String name) {
		check(!this.built);
		this.optName = reassignOptional(this.optName,name);
		return this;
	}

	@Override
	public TournamentBuilder setPriorRanking(Map<String, Integer> ranking) {
		check(!this.built);
		this.optInitialRanking = reassignOptional(this.optInitialRanking,new HashMap<>(ranking));
		this.optFinalRanking = reassignOptional(this.optFinalRanking,new HashMap<>(ranking));
		return this;
	}

	@Override
	public TournamentBuilder addResult(String player, Result result) {
		check(!this.built);
		check(this.optFinalRanking.isPresent());
		check(this.optType.isPresent());
		check(!this.results.containsKey(player));		
		check(result != Result.WINNER || !this.results.containsValue(Result.WINNER));		
		this.results.put(player, result);
		int points = (int)(TYPE_POINTS.get(this.optType.get())*RESULT_FACTOR.get(result));
		this.optFinalRanking.get().merge(player, points, (x,y)->x+y);
		return this;
	}
	
	@Override
	public Tournament build() {
		check(!this.built);
		check(this.optName.isPresent());
		check(this.optType.isPresent());
		check(this.optFinalRanking.isPresent());
		check(this.results.containsValue(Result.WINNER));
		this.built = true;
		return new Tournament() {
			
			private Map<String,Integer> newRanking = Collections.unmodifiableMap(optFinalRanking.get());

			@Override
			public Type getType() {
				return optType.get();
			}

			@Override
			public String getName() {
				return optName.get();
			}

			@Override
			public Optional<Result> getResult(String player) {
				return Optional.ofNullable(results.get(player)).filter(c -> c!=null);
			}

			@Override
			public String winner() {
				for (Entry<String,Result> entry: results.entrySet()) {
					if (entry.getValue()==Result.WINNER) {
						return entry.getKey();
					}
				}
				throw new IllegalStateException();
			}

			@Override
			public Map<String, Integer> initialRanking() {
				return Collections.unmodifiableMap(optInitialRanking.get());
			}
			
			@Override
			public Map<String, Integer> resultingRanking() {
				return Collections.unmodifiableMap(optFinalRanking.get());
			}

			@Override
			public List<String> rank() {
				return this.newRanking.entrySet().stream()
						                         .sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
						                         .map(Entry::getKey)
						                         .collect(Collectors.toList());
			}

		};
	}

}
