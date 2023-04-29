package ex2016.a01a.sol2;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;



public class GUI {
    
    public GUI(String fileName){
        Model m = new ModelImpl(fileName);
        JFrame jf = new JFrame();
        List<JButton> l = m.availableCommands().stream().map(JButton::new).collect(Collectors.toList());
        l.forEach(jb -> jb.addActionListener(e -> m.execCommand(((JButton)e.getSource()).getText())));
        JButton jbOK = new JButton("OK");
        jbOK.addActionListener(e -> System.out.println(m.getAllNumbersAndReset()));
        JPanel jp = new JPanel();
        l.forEach(jp::add);
        jp.add(jbOK);
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }
    
    public static void main(String[] s){
        new GUI("a.txt");
    }

}
