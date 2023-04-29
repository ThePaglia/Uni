package a06.sol2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final JButton advance = new JButton("ADVANCE");
    private final Logics logics;
    private boolean advancing = false;
    
    public GUI(int size) {
    	this.logics = new LogicsImpl(10);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,advance);
        
        advance.addActionListener(e -> {
        	if (!advancing) {
        		advancing = true;
        		cells.keySet().forEach(jb -> jb.setEnabled(false));
        	}
        	var opt = logics.advance();
        	if (opt.isPresent()) {
        		for (var entry: cells.entrySet()) {
        			if (entry.getValue().equals(opt.get())) {
        				entry.getKey().setText("*");
        			}
        		}
        	} else {
        		System.exit(0);
        	}
        });
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	var position = cells.get(jb);
        	if (logics.select(position.getX(), position.getY())) {
        		jb.setText("O");
        	}
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
    }
}