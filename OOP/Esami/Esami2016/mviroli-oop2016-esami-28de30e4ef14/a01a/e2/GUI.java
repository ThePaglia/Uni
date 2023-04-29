package ex2016.a01a.e2;

import javax.swing.*;

public class GUI {
    
    public GUI(String fileName){
        JFrame jf = new JFrame();
        JButton jb1 = new JButton("1");
        JButton jb2 = new JButton("2");
        JButton jbOK = new JButton("OK");
        jbOK.addActionListener(e -> System.exit(1));
        JPanel jp = new JPanel();
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jbOK);
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }

}
