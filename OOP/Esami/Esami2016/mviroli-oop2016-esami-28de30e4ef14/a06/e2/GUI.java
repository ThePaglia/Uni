package pr2016.a06.e2;

import javax.swing.*;


public class GUI {
        
    public GUI(){
        JButton jb1 = new JButton("1");
        JButton jb2 = new JButton("2");
        JButton jb3 = new JButton("3"); 
        jb1.addActionListener(e -> {
            jb1.setText("--");
            jb1.setEnabled(false);
        });
        JPanel jp = new JPanel();
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }
    
    public static void main(String[] s){
        new GUI();
    }

}
