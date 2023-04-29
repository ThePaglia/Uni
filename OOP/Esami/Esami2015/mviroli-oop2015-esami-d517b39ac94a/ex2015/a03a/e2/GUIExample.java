package ex2015.a03a.e2;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class GUIExample extends JFrame {
    
    private static final long serialVersionUID = -8642972112319080520L;
    private static final String[] CHOICES = {"A", "B", "C", "D"};
    private final JComboBox<String> question = new JComboBox<>(CHOICES);
    private final JButton ok = new JButton("OK");
    
    public GUIExample(){
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        
        JPanel panel = new JPanel(new GridLayout(2,2));
        panel.add(flowBoxed(new JLabel("ciao")));
        panel.add(flowBoxed(question));
        panel.add(flowBoxed(new JLabel("ciao2")));
        panel.add(flowBoxed(ok));
        
        ok.addActionListener(e->JOptionPane.showMessageDialog(GUIExample.this, "hai scelto l'opzione "+CHOICES[question.getSelectedIndex()]));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        JPanel south = new JPanel();
        south.add(new JButton("Quit"));
        this.getContentPane().add(BorderLayout.SOUTH,south);
        this.setVisible(true);
    }
    
    private JPanel flowBoxed(JComponent jc){
        JPanel jp = new JPanel(new FlowLayout());
        jp.add(jc);
        return jp;
    }
    
    public static void main(String[] args){
        new GUIExample();
    }
        
}
