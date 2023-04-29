package ex2016.a02a.e2;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;

public class GUI {
    
    
    public GUI(int size){
        JButton b1 = new JButton("  ");
        JButton b2 = new JButton("  ");
        b1.addActionListener(e -> b1.setText("*"));
        b2.addActionListener(e -> b2.setText("*"));
        JPanel jp = new JPanel();
        jp.add(b1);
        jp.add(b2);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }
    
    public static void main(String[] s){
        new GUI(10);
    }

}
