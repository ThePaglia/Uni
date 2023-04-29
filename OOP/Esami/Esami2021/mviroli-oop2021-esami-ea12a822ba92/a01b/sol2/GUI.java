package a01b.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
        	var position = cells.get(button);
        	var result = logics.hit(position.getX(), position.getY());
        	System.out.println(result);
        	switch (result) {
        	case FIRST: button.setText("1"); break;
        	case SECOND: button.setText("2"); break;
        	case THIRD: button.setText("3"); for (var entry: cells.entrySet()) {
        		if (logics.isSelected(entry.getValue().getX(),entry.getValue().getY())) {
        			entry.getKey().setText("*");
        		}
        		entry.getKey().setEnabled(false);
         	}
        	break;
        	case WRONG: 
        	}
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb,new Pair<>(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
