package a03c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> field = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	if (this.logics.hit()) {
                draw();
            } else {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(i,j);
                final JButton jb = new JButton(" ");
                this.field.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        draw();
        this.setVisible(true);
    }
    private void draw() {
        for (var elem : field.keySet()) {
            if (this.logics.getValue(this.field.get(elem)).isPresent()) {
                elem.setText(this.logics.getValue(this.field.get(elem)).get() ? "*" : "O");
            } else {
                elem.setText("");
            }
        }
    }
}
