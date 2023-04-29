package a03b.sol2;

import java.util.*;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {
		
	private BGrid bg;
    
	public LogicsImpl(int size) {
		this.bg = new BGrid(size);
	}
    
	@Override
	public void hit(int row, int col) {
		final boolean val = !this.bg.get(row, col);
		for (int i=0;i<this.bg.size();i++) {
			for (int j=0;j<this.bg.size();j++) {
				if (i+j==row+col) {
					this.bg.set(i,j,val);
				}
			}
		}
	}

	@Override
    public boolean getCell(int row, int col) {
		return this.bg.get(row,col);
	}
	
	private static class BGrid {

		private List<List<Boolean>> grid = new ArrayList<>();
	    
	    public BGrid(int size){
	        for (int i=0; i<size; i++) {
	        	final List<Boolean> row = new ArrayList<>(); 
	        	this.grid.add(row);
	        	for (int j=0; j<size; j++) {
	        		row.add(false);
	        	}
	        }
	    }
	    
	    public int size() {
	    	return this.grid.get(0).size();
	    }
	    
	    public boolean get(int row, int col) {
	    	return this.grid.get(row).get(col);
	    }
	    
	    public void set(int row, int col,boolean b) {
	    	this.grid.get(row).set(col,b);
	    }
	    	
	}


}
