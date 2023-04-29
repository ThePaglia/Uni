package a02a.sol2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GUIExample extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private List<JButton> buttons;
    private final JButton left;
    
    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 40);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText("*");
            bt.setEnabled(false);
        };
        
        JPanel panel = new JPanel(new FlowLayout());
        this.buttons = IntStream.range(0,5)
        		                .mapToObj(i->new JButton("  "))
        		                .peek(panel::add)
        		                .peek(jb->jb.addActionListener(al))
        		                .collect(Collectors.toList());
        
        this.left = new JButton("<");
        panel.add(this.left);
        this.getContentPane().add(BorderLayout.CENTER,panel);
        this.pack();
        this.setVisible(true);
    }
    public static void main(String[] args) {
    	new GUIExample();
    }
    
}
