package a01a.sol2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class GUIExample extends JFrame {
    
    private final List<JButton> buttons = new ArrayList<>();
    
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300,300);
        
        JPanel panel = new JPanel(new GridLayout(3,3));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText("pos"+buttons.indexOf(bt));
        };
                
        for (int i=0; i<9; i++){
            final JButton jb = new JButton(" ");
            jb.addActionListener(al);
            this.buttons.add(jb);
            panel.add(jb);
        }
        this.setVisible(true);
    } 
    
    public static void main(String[] args) {
    	new GUIExample();
    }
}
