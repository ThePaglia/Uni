package a05.sol2;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class LogicsImpl implements Logics {
	
	private EnumMap<Player,Integer> points = new EnumMap<>(Player.class);
	private Random random = new Random();
	private Supplier<Integer> randomGenerator = ()->random.nextInt(6)+1;
	
	public LogicsImpl(int aPoints, int bPoints) {
		super();
		points.put(Player.A,aPoints);
		points.put(Player.B,aPoints);
	}

	@Override
	public int getPoints(Player player) {
		return points.get(player);
	}

	@Override
	public EnumMap<Player,Integer> draw() {
		EnumMap<Player,Integer> draw = new EnumMap<>(Player.class);
		draw.put(Player.A,randomGenerator.get());
		draw.put(Player.B,randomGenerator.get());
		if (draw.get(Player.A)>=draw.get(Player.B)) {
			points.merge(Player.B, 0, (i,j)->i-1);
		}
		if (draw.get(Player.B)>=draw.get(Player.A)) {
			points.merge(Player.A, 0, (i,j)->i-1);
		}
		return draw;
	}

	@Override
	public Optional<Player> winner() {
		return this.points.get(Player.A)==0 ? Optional.of(Player.B) :
			this.points.get(Player.B)==0 ? Optional.of(Player.A) : Optional.empty();
	}

}
