package ex2015.a04.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Model model;
    
    public GUI(int rows, int cols, int hits, int attempts) {
        this.model = new ModelImpl(rows,cols,attempts,hits);
        System.out.println(model.getSecretPositions());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        
        JPanel panel = new JPanel(new GridLayout(rows,cols));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> p = buttons.get(bt);
            final Optional<Boolean> res = model.hit(p.getX(), p.getY());
            if (res.isPresent()){
                bt.setText(res.get()?"XX":"OO");
                bt.setEnabled(false);
            }
            if (model.won() || model.getRemainingAttempts()==0){
                JOptionPane.showMessageDialog(GUI.this, model.won()?"You won":"You lost");
                System.exit(1);
            } 
        };
                
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                final JButton jb = new JButton(GUI.getButtonName(i, j));
                jb.addActionListener(al);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private static String getButtonName(int row, int col){
        return ""+((char)('A'+row))+(col+1);
    }
    
    public static void main(String[] args){
        new GUI(5,3,2,4);
    }
        
}
