package a05a.sol2;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUIExample extends JFrame {
    
    private List<JButton> list = new ArrayList<>();
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        int cols = 3; // {1,2,3,4,5}
        JPanel panel = new JPanel(new GridLayout(cols,cols));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        this.getContentPane().add(BorderLayout.SOUTH,new JButton("South"));
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText(""+list.indexOf(bt));
        };
        for (int i=0;i<3*3;i++){
            final JButton jb = new JButton("a");
            jb.addActionListener(al);
            list.add(jb);
            panel.add(jb);
        } 
        this.setVisible(true);
    }

        
    public static void main(String[] args){
        new GUIExample();
    }
        
}
