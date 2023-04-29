package a01b.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size, int mines) {
        this.logics = new LogicsImpl(size,mines);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> p = buttons.get(bt);
            //System.out.println("hit "+p);
            Optional<Integer> result = logics.hit(p.getX(), p.getY());
            if (result.isPresent() && !logics.won()) {
            	bt.setText(String.valueOf(result.get()));
            	bt.setEnabled(false);
            } else {
            	System.out.println(logics.won() ? "WON" : "LOST");
            	System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(al);
                this.buttons.put(jb,new Pair<>(j,i));
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
