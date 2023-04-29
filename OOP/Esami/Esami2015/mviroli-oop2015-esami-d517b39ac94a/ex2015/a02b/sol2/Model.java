package ex2015.a02b.sol2;

import java.util.*;

public interface Model {
    
    enum Result {
        NO, AMBO, TOMBOLINA;
    }
    
    int getNumCells();
    
    int getMaxNum();
    
	
	List<Integer> getValues();
	
	Result numberDrawn(int i);
	
}
