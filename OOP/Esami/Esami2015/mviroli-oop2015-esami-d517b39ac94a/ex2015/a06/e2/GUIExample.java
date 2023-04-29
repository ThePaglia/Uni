package ex2015.a06.e2;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIExample extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ActionListener al = e -> {
            JButton jb = (JButton)e.getSource();
            if (jb.getText().equals("X")){
                JOptionPane.showMessageDialog(GUIExample.this, "End");
                System.exit(0);
            }
            jb.setText("X");
        };
        JPanel up = new JPanel();
        for (int i=0; i<10; i++){
            JButton jb = new JButton(" ");
            jb.addActionListener(al);
            up.add(jb);
        }
        
        JPanel down = new JPanel();
        for (int i=0; i<6; i++){
            JButton jb = new JButton(String.valueOf(i+1));
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
    
    public static void main(String[] s){
        new GUIExample();
    }

}
