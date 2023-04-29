package ex2015.a03b.sol2;

import javax.swing.*;

import java.awt.BorderLayout;

public class GUIExample extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    
    private final JButton jNext = new JButton("Next");
    
    public GUIExample(){
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200, 200);
        
        
        Box panel = Box.createVerticalBox();
        JLabel lab = new JLabel("aaa");
        panel.add(lab);
        panel.add(new JLabel("bbb"));
        panel.add(new JLabel("bbb"));
        panel.add(new JLabel("bbb"));
        panel.add(new JLabel("bbb"));
        
        this.jNext.addActionListener(e -> lab.setText(lab.getText()+"*"));
        
        this.getContentPane().add(BorderLayout.CENTER,panel);
        JPanel south = new JPanel();
        south.add(jNext);
        this.getContentPane().add(BorderLayout.SOUTH,south);
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new GUIExample();
    }
        
}
