package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Logics logics;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                var pos = cells.get(button);
                logics.hit(pos);
                draw(logics.getBitches());
                if (logics.isOver()) {
                    System.exit(0);
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer,Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    private void draw(Set<Pair<Integer, Integer>> set) {
        this.cells.forEach((k, v) -> k.setText(set.contains(v) ? "*" : " "));
    }
}
