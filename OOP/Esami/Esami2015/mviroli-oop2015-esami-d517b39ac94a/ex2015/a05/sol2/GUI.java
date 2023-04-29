package ex2015.a05.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Map<Pair<Integer,Integer>,JButton> pos = new HashMap<>();
    private final Model model;
    
    public GUI(int rows, int cols) {
        this.model = new ModelImpl(rows,cols);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        
        JPanel panel = new JPanel(new GridLayout(rows,cols));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> p = buttons.get(bt);
            final Optional<Pair<Integer,Integer>> res = model.hit(p.getX(), p.getY());
            if (res.isPresent()){
                bt.setText("");
                pos.get(res.get()).setText("O");
                if (model.over()){
                    JOptionPane.showMessageDialog(GUI.this, "Game over.. bye bye");
                    System.exit(0);
                }
            }
        };
                
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                final JButton jb = new JButton(model.get(i, j) ? "O" : "");
                jb.addActionListener(al);
                this.buttons.put(jb,new Pair<>(i,j));
                this.pos.put(new Pair<>(i,j),jb);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new GUI(4,4);
    }
        
}
