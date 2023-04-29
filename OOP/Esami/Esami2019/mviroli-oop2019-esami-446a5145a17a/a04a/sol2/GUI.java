package a04a.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final JButton moveButton = new JButton(">");
    private final Logics logics;
    private boolean flip = false;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        this.getContentPane().add(BorderLayout.SOUTH,moveButton);
        
        moveButton.addActionListener(e -> {
        	logics.tick();
        	this.updateView();
        	if (logics.isOver()) {
        		System.exit(0);
        	}
        	this.flip = !this.flip;
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb,new Pair<>(j,i));
                panel.add(jb);
            }
        }
        this.updateView();
        this.setVisible(true);
    }
    
    private void updateView() {
    	Pair<Integer,Integer> pos = logics.getPosition();
    	cells.forEach((b,p)-> b.setText( pos.equals(p) ? (this.flip?"0":"1") : " "));
    }
    
}
