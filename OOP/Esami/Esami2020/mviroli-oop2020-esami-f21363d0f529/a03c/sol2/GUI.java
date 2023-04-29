package a03c.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final JButton next = new JButton(">");
    private final Logics logics;
    private final boolean turn = true;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel,BorderLayout.CENTER);
        this.getContentPane().add(next,BorderLayout.SOUTH);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
        	var position = cells.get(button);
        	if (logics.hit(position)) {
        		updateView();
        	}
        };
        
        next.addActionListener(e -> {
        	if (logics.next()){
        		updateView();
        	}
        });
        
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                var p = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.put(jb,new Pair<>(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.updateView();
        this.setVisible(true);
    }

	private void updateView() {
		var positions = logics.getPositions();
		this.cells.forEach( (b,p) -> {
			Optional.of(positions.indexOf(p)).filter(i->i>=0).map(i -> positions.size()-i-1).ifPresent(i->b.setText(String.valueOf(i)));
		});
	}
    
}
