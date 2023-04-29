package a03a.sol2;

import javax.swing.*;
import java.util.*;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Map<Pair<Integer,Integer>,JButton> invButtons = new HashMap<>();
    private final Logics logics;
    private final static int SIZE = 6;
    
    public GUI() {
        this.logics = new LogicsImpl(SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*SIZE, 50*SIZE);
        
        JPanel panel = new JPanel(new GridLayout(SIZE,SIZE));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = buttons.get(bt);
            logics.hit(pos.getX(), pos.getY());
            if (logics.isDone()) {
            	System.exit(0);
            }
            draw();
        };
                
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(al);
                this.buttons.put(jb,new Pair<>(i,j));
                this.invButtons.put(new Pair<>(i,j),jb);
                panel.add(jb);
            }
        }
        draw();
        this.setVisible(true);
    }
    
    private void draw() {
        for (int i=0;i<SIZE;i++) {
        	for (int j=0; j<SIZE;j++) {
        		int val = logics.getMark(i, j);
        		JButton jb = this.invButtons.get(new Pair<>(i,j));
        		jb.setText(val == 0 ? "" : String.valueOf(val));
        		if (val == 5) {
        			jb.setEnabled(false);
        		}
        	}
        }
    }
        
}
