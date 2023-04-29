package ex2015.a05.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class GUIExample extends JFrame {
    
    
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        JPanel panel = new JPanel(new GridLayout(3,3));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText("X");
            JOptionPane.showMessageDialog(GUIExample.this, "bye bye");
            System.exit(1);
        };
        for (int i=0;i<3*3;i++){
            final JButton jb = new JButton("0");
            jb.addActionListener(al);
            panel.add(jb);
        } 
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new GUIExample();
    }
        
}
