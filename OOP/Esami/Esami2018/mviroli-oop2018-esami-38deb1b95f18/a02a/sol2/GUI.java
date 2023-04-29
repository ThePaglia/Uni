package a02a.sol2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private List<JButton> buttons = null; // Java hack to ensure initialisation :(
    private final JButton left;
    private final JButton right;
    private final Logics logics;
    private final static int SIZE = 5;
    
    public GUI() {
        this.logics = new LogicsImpl(SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*(SIZE*3), 40);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            this.logics.select(buttons.indexOf(bt));
            drawAndPossiblyQuit();
        };
        
        JPanel panel = new JPanel(new FlowLayout());
        this.buttons = IntStream.range(0,SIZE)
        		                .mapToObj(i->new JButton(""))
        		                .peek(panel::add)
        		                .peek(jb->jb.addActionListener(al))
        		                .collect(Collectors.toList());
        
        this.left = new JButton("<");
        panel.add(this.left);
        this.left.addActionListener(e -> { 
        	logics.left(); 
        	drawAndPossiblyQuit();
        });
        this.right = new JButton(">");
        panel.add(this.right);
        this.right.addActionListener(e -> { 
        	logics.right(); 
        	drawAndPossiblyQuit();
        });
        this.getContentPane().add(BorderLayout.CENTER,panel);
        this.drawAndPossiblyQuit();
        this.pack();
        this.setVisible(true);
    }
    
    private void drawAndPossiblyQuit() {
    	boolean b = logics.isDone();
    	List<Integer> numbers = this.logics.getNumbers();
    	for (int i=0; i<SIZE; i++) {
    		this.buttons.get(i).setText(String.valueOf(numbers.get(i)));
    		this.buttons.get(i).setEnabled(!b && this.logics.getSelected() != i);
    	}
    }
    
}
