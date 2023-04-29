package a01b.sol2;

import java.util.Set;

public interface Logics{
	
	enum HitType {
		FIRST, SECOND, THIRD, WRONG;
	}
	
	HitType hit(int x, int y);
	
	boolean isSelected(int x, int y);
	
}
