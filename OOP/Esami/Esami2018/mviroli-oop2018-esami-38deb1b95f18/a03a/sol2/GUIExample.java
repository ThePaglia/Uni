package a03a.sol2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class GUIExample extends JFrame {
    
	private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> buttons = new LinkedList<>();
    
    public GUIExample() {
    	
        this.setSize(200,200);
        
        JPanel panel = new JPanel(new GridLayout(4,4));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText("*");
            bt.setEnabled(false);
            if (buttons.indexOf(bt)==0){
            	System.out.println("Exit here!!!");
            }
        };
                
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(al);
                this.buttons.add(jb);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
        
    public static void main(String[] args) {
    	new GUIExample();
    }
}
