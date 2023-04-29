package a02b.sol2;

import javax.swing.*;
import java.util.*;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Map<Pair<Integer,Integer>,JButton> invButtons = new HashMap<>();
    private Optional<JButton> selected = Optional.empty();
    private final Logics logics;
    private final static int SIZE = 10;
    
    public GUI() {
        this.logics = new LogicsImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*SIZE, 50*SIZE);
        
        JPanel panel = new JPanel(new GridLayout(SIZE,SIZE));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = buttons.get(bt);
            if (selected.isPresent()) {
            	selected.get().setEnabled(true);
            	if (logics.hit2(pos.getX(),pos.getY())) {
                	System.exit(0);
            	}
        		for (Pair<Integer,Integer> p: logics.marksIterator()) {
        			invButtons.get(p).setText("*");
        		}
        		selected = Optional.empty();
            } else {
            	selected = Optional.of(bt);
            	bt.setEnabled(false);
            	logics.hit1(pos.getX(),pos.getY());
            }
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
        this.setVisible(true);
    }
        
}
