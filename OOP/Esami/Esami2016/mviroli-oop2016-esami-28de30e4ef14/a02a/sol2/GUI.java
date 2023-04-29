package ex2016.a02a.sol2;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;

public class GUI {
    
    private List<JButton> buttons;
    private Model model;
    
    public GUI(int size){
        model = new ModelImpl(size);
        buttons = IntStream.range(0,size).mapToObj(i->new JButton(" ")).collect(Collectors.toList());
        buttons.forEach(jb -> jb.addActionListener(e -> {
            model.disable(buttons.indexOf(e.getSource()));
            viewUpdate();
        }));
        JButton jbMove = new JButton("Move");
        jbMove.addActionListener(e -> { 
            model.move(); 
            viewUpdate(); 
            System.out.println(model);
        });
        JButton jbReset = new JButton("Reset");
        jbReset.addActionListener(e -> { model.reset(); viewUpdate(); });
        JPanel jp = new JPanel();
        buttons.forEach(jp::add);
        jp.add(jbMove);
        jp.add(jbReset);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
        this.viewUpdate();
    }
    
    private void viewUpdate(){
        Set<Integer> disabled = model.allDisabled();
        this.buttons.forEach(jb -> jb.setEnabled(!disabled.contains(buttons.indexOf(jb))));
        this.buttons.forEach(jb -> jb.setText(buttons.indexOf(jb)==model.getPosition()?"*":" "));
    }
    
    public static void main(String[] s){
        new GUI(10);
    }

}
