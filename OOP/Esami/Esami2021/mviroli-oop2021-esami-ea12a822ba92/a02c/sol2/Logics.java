package a02c.sol2;

public interface Logics{
	
	public boolean next();
	
	Pair<Integer,Integer> getNext();
	
	boolean isObstacle(int x, int y);
}
