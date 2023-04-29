package a02a.e2;

import javax.swing.*;
import java.util.*;

import java.awt.*;


public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Logics logics;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final JButton next = new JButton(">");
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,next);
        
        next.addActionListener(e -> {
            if (logics.hit()) {
                this.draw();
            }
            if (logics.isOver()) {
                this.next.setEnabled(false);
            }
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer, Integer>(i,j));
                grid.add(jb);
            }
        }
        draw();
        this.setVisible(true);
    }
        private void draw() {
            for (var c : cells.entrySet()) {
                if (c.getValue().equals(logics.getPawn())) {
                    c.getKey().setText("P");
                } else if (logics.getEnemies().contains(c.getValue())) {
                    c.getKey().setText("*");
                } else {
                    c.getKey().setText(" ");
            }
        }
    }
}
