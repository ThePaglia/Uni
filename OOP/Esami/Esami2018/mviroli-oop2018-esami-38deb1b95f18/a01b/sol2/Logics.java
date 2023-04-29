package a01b.sol2;

public interface Logics{
    
	/**
     * click ona cell either to select Bishop's start or end position
     * 
     * @param row
     * @param col
     * @return whether the bishop returned home
     */
    boolean hit(int row, int col);
    
    /**
     * @param row
     * @param col
     * @return whether position row,col has the bishop
     */
    boolean hasBishop(int row, int col);
    
    /**
     * @param row
     * @param col
     * @return whether position row,col is a valid target to move to
     */
    boolean isEnabled(int row, int col);
}
