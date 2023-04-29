package ex2015.a04.e2;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class GUIExample extends JFrame {
    
    
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        int cols = 1+getRandom(5); // {1,2,3,4,5}
        JPanel panel = new JPanel(new GridLayout(3,cols));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText("X");
            JOptionPane.showMessageDialog(GUIExample.this, "bye bye");
            System.exit(1);
        };
        for (int i=0;i<3*cols;i++){
            final JButton jb = new JButton(getButtonName(i));
            jb.addActionListener(al);
            panel.add(jb);
        } 
        this.setVisible(true);
    }

    private static String getButtonName(int i){
        return ""+((char)('A'+i));
    }
    
    private static int getRandom(int max){
        return new Random().nextInt(max);
    }
    
    public static void main(String[] args){
        new GUIExample();
    }
        
}
