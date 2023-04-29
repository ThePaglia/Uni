package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> buttons = new ArrayList<>();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        JButton north = new JButton("NORTH");
        this.getContentPane().add(BorderLayout.NORTH,north);
        this.getContentPane().add(BorderLayout.SOUTH,new JButton("SOUTH"));
        this.getContentPane().add(BorderLayout.EAST,new JButton("EAST"));
        this.getContentPane().add(BorderLayout.WEST,new JButton("WEST"));
        
        north.addActionListener(e -> {
        	System.out.println("north");
        });
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	jb.setText(""+buttons.indexOf(jb));
        	
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.buttons.add(jb);
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
    }    
}
