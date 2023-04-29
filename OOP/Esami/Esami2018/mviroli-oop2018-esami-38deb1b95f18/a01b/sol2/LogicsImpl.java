package a01b.sol2;

public class LogicsImpl implements Logics {
	
	private Pair<Integer,Integer> bishop;
	private boolean selected = false;
	 
    public LogicsImpl(int size, int bishops){
    	this.bishop = new Pair<>(0,0);
    }
    
	@Override
	public boolean hit(int row, int col) {
		Pair<Integer,Integer> pos = new Pair<>(row,col);
		if (this.selected && isEnabled(row,col)) {
			this.bishop = pos;
			this.selected = false;
			return (row==0 && col==0);
		} 
		this.selected = this.hasBishop(row, col);
		return false;
	}

	@Override
	public boolean hasBishop(int row, int col) {
		return this.bishop.getX()==row && this.bishop.getY()==col;
	}

	@Override
	public boolean isEnabled(int row, int col) {
		return !this.selected ||
				(row-col == this.bishop.getX()-this.bishop.getY() ) ||
				(row+col == this.bishop.getX()+this.bishop.getY() );
	}
}

