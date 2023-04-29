package ex2016.a04.sol2;

import javax.swing.*;


public class GUI {
    
    private JButton left = new JButton("l");
    private JButton right  = new JButton("r");
    private JComboBox<String> box;
    private Model model = new ModelImpl();
    
    public GUI(){
        box = new JComboBox<>();
        model.choices().forEach(s -> box.addItem(s));
        box.addActionListener(e -> {
            @SuppressWarnings("rawtypes")
            String item = ((String)((JComboBox)e.getSource()).getSelectedItem());
            model.choose(item);
        });
        left.addActionListener(e -> {
            model.hitLeft();
            if (model.shouldQuit()){
                System.exit(0);
            }
        });
        right.addActionListener(e -> {
            model.hitRight();
            if (model.shouldQuit()){
                System.exit(0);
            }
        });
        JPanel jp = new JPanel();
        jp.add(box);
        jp.add(left);
        jp.add(right);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
    }
    
    public static void main(String[] s){
        new GUI();
    }

}
