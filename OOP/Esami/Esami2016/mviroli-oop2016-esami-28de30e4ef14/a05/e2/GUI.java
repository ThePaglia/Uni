package pr2016.a05.e2;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;

public class GUI {
        
    public GUI(){
        JButton jbMove = new JButton("true");
        jbMove.addActionListener(e -> {
            System.exit(0);
        });
        JCheckBox jCheck = new JCheckBox("Check",true);
        jCheck.addActionListener(e -> { jbMove.setText(""+jCheck.isSelected()); });
        JPanel jp = new JPanel();
        jp.add(jbMove);
        jp.add(jCheck);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }
    
    public static void main(String[] s){
        new GUI();
    }

}
