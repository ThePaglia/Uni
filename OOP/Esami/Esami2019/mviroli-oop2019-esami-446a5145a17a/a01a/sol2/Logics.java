package a01a.sol2;

public interface Logics{
	
	enum Result {
		HIT, MISS, WON, LOST
	}
    
    Result hit(int row, int col);
    
}
