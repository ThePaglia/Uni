package pr2016.a06.sol2;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.*;

public class GUI {
    
    private List<JButton> buttons;
    private Model model;
    
    public GUI(int size){
        model = new ModelImpl(size);
        ActionListener al = e -> {
            model.hit(buttons.indexOf(e.getSource()));
            viewUpdate();
        };
        buttons = IntStream.range(0,size)
                           .mapToObj(i->new JButton(""))
                           .peek(jb -> jb.addActionListener(al))
                           .collect(Collectors.toList());
        JPanel jp = new JPanel();
        buttons.forEach(jp::add);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        this.viewUpdate();
        jf.setSize(600,100);
        jf.setVisible(true);
    }
    
    private void viewUpdate(){
        for (int i=0;i<buttons.size();i++){
            buttons.get(i).setText(model.text(i));
            buttons.get(i).setEnabled(model.isEnabled(i));
        }
    }
    
    public static void main(String[] s){
        new GUI(10);
    }

}
