package a02a.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer,Integer>,JButton> cells = new HashMap<>();
    private final JButton next = new JButton(">");
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,next);
        
        next.addActionListener(e -> {
        	logics.next();
        	this.updateView();
        	if (logics.isOver()) {
        		this.next.setEnabled(false);
        	}
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Pair<>(j,i),jb);
                grid.add(jb);
            }
        }
        this.updateView();
        this.setVisible(true);
    }
    
    private void updateView() {
    	Set<Pair<Integer,Integer>> enemies = this.logics.enemyPositions();
    	cells.forEach((p,b) -> {
    		b.setText(this.logics.pawnPosition().equals(p) ? "p" : 
    					enemies.contains(p) ? "*" : " ");
    	});
    }
    
}
