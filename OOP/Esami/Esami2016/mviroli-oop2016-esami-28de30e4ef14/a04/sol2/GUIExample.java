package ex2016.a04.sol2;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIExample {
    
    private JButton b = new JButton("Exit");
    private JComboBox<String> box;
    
    public GUIExample(){
        box = new JComboBox<>();
        box.addItem("A");
        box.addItem("B");
        ActionListener al = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("rawtypes")
                String item = ((String)((JComboBox)e.getSource()).getSelectedItem());
                System.out.println(item);
            }
        };
        box.addActionListener(al);
        b.addActionListener(e -> {System.exit(0);});
        JPanel jp = new JPanel();
        jp.add(box);
        jp.add(b);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }
    
    public static void main(String[] s){
        new GUIExample();
    }

}
