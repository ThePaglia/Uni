package a05.sol2;

import java.util.Optional;

public interface Logics{
    
    /**
     * selects a cell
     * 
     * @param row
     * @param col
     * @return the incremental number, optionally, that is, only if accepted
     */
    Optional<Integer> hit(int row, int col);
            
    /**
     * @return if there are no more cells to hit
     */
    boolean end();
}
