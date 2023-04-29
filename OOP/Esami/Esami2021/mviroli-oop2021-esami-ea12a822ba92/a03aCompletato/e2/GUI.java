package a03a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logics logics;
    
    public GUI(int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
            var pos = this.cells.get(jb);
            if (this.logics.hit(pos)) {
                this.logics.updateSelected(pos);
                draw();
            }
            if (this.logics.isOver()) {
                disableAll();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(i, j);
                final JButton jb = new JButton(" ");
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    private void draw() {
        for (var entry : cells.entrySet()) {
            if (this.logics.isSelected(entry.getValue())) {
                entry.getKey().setText("*");
            }
        }
    }
    private void disableAll() {
        for (var entry : cells.entrySet()) {
            entry.getKey().setEnabled(false);;
        }
    }
}
