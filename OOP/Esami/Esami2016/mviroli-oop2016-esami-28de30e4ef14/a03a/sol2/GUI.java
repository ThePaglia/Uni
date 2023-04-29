package ex2016.a03a.sol2;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;

public class GUI {
    
    private List<JButton> buttons;
    private Model model;
    
    public GUI(int size){
        model = new ModelImpl(size);
        buttons = IntStream.range(0,size).mapToObj(i->new JButton(" ")).collect(Collectors.toList());
        buttons.forEach(jb -> jb.setEnabled(false));
        JButton jbMoveA = new JButton("MoveA");
        jbMoveA.addActionListener(e -> { 
            model.moveA(); 
            viewUpdate(); 
        });
        JButton jbMoveB = new JButton("MoveB");
        jbMoveB.addActionListener(e -> { 
            model.moveB(); 
            viewUpdate(); 
        });
        JButton jbReset = new JButton("Reset");
        jbReset.addActionListener(e -> { 
            model.reset(); 
            viewUpdate(); 
        });
        JPanel jp = new JPanel();
        buttons.forEach(jp::add);
        jp.add(jbMoveA);
        jp.add(jbMoveB);
        jp.add(jbReset);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        this.viewUpdate();
        jf.pack();
        jf.setVisible(true);
    }
    
    private void viewUpdate(){
        this.buttons.forEach(jb -> jb.setText(
                buttons.indexOf(jb)==model.getAPosition()?"A":
                    buttons.indexOf(jb)==model.getBPosition()?"B":" "));
        if (model.over()){
            System.exit(0);
        }
    }
    
    public static void main(String[] s){
        new GUI(10);
    }

}
