package a05.sol2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
    	this.logics = new LogicsImpl(5);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	var position = cells.get(jb);
        	if (logics.hit(position.getX(), position.getY())) {
        		jb.setEnabled(false);
        	}
        	if (logics.computeResult().equals(Optional.of(false))) {
        		System.exit(0);
        	}
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(logics.isCellValue(i, j) 
                		? logics.getCellAsValue(i, j).get()?"True":"False"
                		: logics.getCellAsOperation(i, j).get().getName());
                this.cells.put(jb, new Pair<>(i,j));
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
    }
}