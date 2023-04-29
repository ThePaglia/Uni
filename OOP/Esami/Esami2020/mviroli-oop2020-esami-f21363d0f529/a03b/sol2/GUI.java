package a03b.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener el = e -> {
        	var button = (JButton)e.getSource();
        	var position = this.cells.get(button);
        	logics.hit(position);
        	this.updateView();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                grid.add(jb);
                jb.addActionListener(el);
            }
        }
        this.updateView();
        this.setVisible(true);
    }
    
    private void updateView() {
    	Set<Pair<Integer,Integer>> enemies = this.logics.enemyPositions();
    	Set<Pair<Integer,Integer>> enabled = this.logics.enabledPositions();
    	cells.forEach((b,p) -> {
    		b.setText(this.logics.bishopPosition().equals(p) ? "B" : 
    					enemies.contains(p) ? "*" : " ");
    		b.setEnabled(enabled.contains(p));
    	});
    }
    
}
