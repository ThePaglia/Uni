package ex2016.a01b.sol2;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;


public class GUI {
    
    private List<JButton> buttonList;
    private Model model;
    private String fileName; 
    
    public GUI(String fileName, int size) throws Exception {
        this.fileName = fileName;
        JFrame jf = new JFrame();
        this.buttonList = IntStream.range(0, size).mapToObj(String::valueOf).map(JButton::new).collect(Collectors.toList());
        buttonList.forEach(jb -> jb.addActionListener(e -> setButtons()));
        JButton jbReset = new JButton("Reset");
        jbReset.addActionListener(e -> reset());
        JPanel jp = new JPanel();
        buttonList.forEach(jp::add);
        jp.add(jbReset);
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
        this.reset();
    }
    
    private void setButtons(){
        int index = 0;
        int pos = this.model.hasNext() ? this.model.next() : -1;
        System.out.println(pos);
        for (JButton jb: this.buttonList){
            jb.setEnabled(index == pos);
            index++;
        }
    }
    
    private void reset(){
        this.model = new ModelImpl(fileName);
        this.setButtons();
    }
    
    public static void main(String[] s) throws Exception {
        new GUI("b.txt",10);
    }
}
