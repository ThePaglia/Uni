package ex2015.a06.sol2;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private static final String EMPTY = " ";
    private static final String FULL = "X";
    private static final String FALL = "*";
    
    public GUI(int size, int pitfall) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final GameModel gm = new GameModelImpl(size,pitfall);
        final List<JButton> posButtons = new ArrayList<>();
        
        JPanel up = new JPanel();
        for (int i=0; i<size; i++){
            JButton jb = new JButton(EMPTY);
            posButtons.add(jb);
            up.add(jb);
        }
        posButtons.get(gm.getPosition()).setText(FULL);
        posButtons.get(pitfall).setText(FALL);
        
        ActionListener al = e -> {
            JButton jb = (JButton)e.getSource();
            int num = Integer.parseInt(jb.getText());
            posButtons.get(gm.getPosition()).setText(EMPTY);
            gm.advance(num);
            if (gm.won() || gm.lost()){
                JOptionPane.showMessageDialog(GUI.this, gm.won() ? "You Won" : "You Lost");
                System.exit(0);
            }
            posButtons.get(gm.getPosition()).setText(FULL);
            
        };
        JPanel down = new JPanel();
        for (int i=0; i<6; i++){
            JButton jb = new JButton(String.valueOf(i+1));
            jb.addActionListener(al);
            down.add(jb);
        }

        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.add(BorderLayout.NORTH,up);
        pan.add(BorderLayout.SOUTH,down);
        this.getContentPane().add(pan);
        this.pack();
        this.setVisible(true);
    }

}
