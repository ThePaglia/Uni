package a02b.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.NORTH,up);
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,down);
        
        up.addActionListener(e -> {
        	logics.up();
        	this.updateView();
        });
        
        down.addActionListener(e -> {
        	logics.down();
        	this.updateView();
        });
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	var pos = buttons.get(jb);
        	this.logics.hit(pos);
        	jb.setText("*");
        	
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.buttons.put(jb,new Pair<>(j,i));
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.updateView();
        this.setVisible(true);
    }
    
    private void updateView() {
    	Set<Pair<Integer,Integer>> items = this.logics.itemsPositions();
    	buttons.forEach((b,p) -> {
    		b.setText(items.contains(p) ? "*" : " ");
    	});
    }
    
}
