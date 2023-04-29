package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logic;
    private int click = 0;
    
    public GUI(int size) {
        this.logic = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if (logic.hit(position)) {
                    button.setText("*");
                } else {
                    button.setText("");
                }
            }
        };

        ActionListener rs = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                logic.disableDiagonal();
                if (click == 0) {
                    for (var jb : cells.keySet()) {
                        if (logic.isDisabled(cells.get(jb))) {
                            jb.setEnabled(false);
                        }
                    }
                    click = 1;
                } else {
                    if (logic.checkRestart()) {
                        logic.restart();
                        for (var jb : cells.keySet()) {
                            jb.setText("");
                            jb.setEnabled(true);
                        }
                    }
                    click = 0;
                }
            }
        };
        
        final JButton ck = new JButton("Check > Restart");
        ck.addActionListener(rs);
        main.add(BorderLayout.SOUTH, ck);

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
