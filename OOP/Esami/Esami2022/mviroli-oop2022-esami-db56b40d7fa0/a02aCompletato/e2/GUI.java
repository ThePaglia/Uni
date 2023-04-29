package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logic;
    
    public GUI(int size) {
        this.logic = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if (logic.hit(position)) {
                    button.setText("B");
                }
                for (var jb : cells.keySet()) {
                    if (logic.isToBeDisabled(cells.get(jb))) {
                        jb.setEnabled(false);
                    }
                }
                if (logic.canDisable()) {
                    logic.restart();
                    for (var jb : cells.keySet()) {
                        jb.setText("");
                        jb.setEnabled(true);
                    }
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
}
