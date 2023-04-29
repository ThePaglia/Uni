package a03c.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer,Integer>,JButton> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var p = logics.next();
        	if (logics.isOver()) {
        		System.exit(0);
        	} else {
        		this.updateCells(p);
        	}
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.put(pos,jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.updateCells(logics.next());
        this.setVisible(true);
    }

	private void updateCells(Set<Pair<Integer,Integer>> p) {
    	for (var entry: this.cells.entrySet()) {
			entry.getValue().setText(logics.obstacleAt(entry.getKey().getX(),entry.getKey().getY()) ? "O" : " ");
			if (p.contains(entry.getKey())) {
				entry.getValue().setText("*");
			}
		}
	}
    
}
