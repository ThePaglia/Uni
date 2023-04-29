package a05.e2;

import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	var position = cells.indexOf(jb);
        	jb.setText(""+position);
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(""+(i+j));
                this.cells.add(jb);
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
    }
}