package a02a.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private Logic logic;
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                var position = cells.get(button);
                logic.hit(position.getX(), position.getY());
                redraw();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }   
    
    private void redraw(){
        for (var entry: cells.entrySet()){
            entry.getKey().setEnabled(this.logic.isAvailable(entry.getValue().getX(), entry.getValue().getY()));
            entry.getKey().setText(logic.isBishop(entry.getValue().getX(), entry.getValue().getY()) ? "B" : "");
        }
    }
}
