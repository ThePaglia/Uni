package a03c.sol2;

import java.util.*;

public class LogicsImpl implements Logics {
	
	private final List<Pair<Integer, Integer>> hits = new LinkedList<>();
	private final int size;
	private final Random random = new Random();
	
	public LogicsImpl(int size) {
		this.size = size;
		this.hits.add(new Pair<>(this.random.nextInt(size),0));
		this.hits.add(0,new Pair<>(this.hits.get(0).getX(),1));
	}
	
	@Override
	public List<Pair<Integer, Integer>> getPositions() {
		return Collections.unmodifiableList(this.hits);
	}

	private boolean available(Pair<Integer,Integer> position) {
		return position.getX()>=0 && position.getX()<size &&
				position.getY()>=0 && position.getY()<size &&
				!this.hits.contains(position);
	}
	
	private Optional<Pair<Integer,Integer>> nextInLine(){
		var last = this.hits.get(0);
		var butLast = this.hits.get(1);
		return Optional.of(new Pair<>(last.getX()*2-butLast.getX(),last.getY()*2-butLast.getY())).filter(this::available);
	}
	
	private Optional<Pair<Integer,Integer>> nextByHit(Pair<Integer,Integer> position){
		final var last = this.hits.get(0);
		return Optional.of(position).filter(p -> Math.abs(p.getX()-last.getX())+Math.abs(p.getY()-last.getY()) == 1);				
	}
	
	private boolean attemptMove(Optional<Pair<Integer,Integer>> optPosition) {
		return optPosition.stream().peek(p -> this.hits.add(0,p)).findAny().isPresent();
	}

	@Override
	public boolean hit(Pair<Integer,Integer> position) {
		return this.attemptMove(nextByHit(position));
	}
	
	@Override
	public boolean next() {
		return this.attemptMove(nextInLine());
	}

}
