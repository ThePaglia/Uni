package a04.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;
    private final boolean turn = true;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
        	var position = cells.get(button);
        	if (logics.moveHero(position.getX(),position.getY())) {
        		this.updateView();
        	};
        	if (logics.isOver()) {
        		System.exit(0);
        	}
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
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
    	cells.forEach((b,p) -> {
    		b.setText(this.logics.pawnsPositions().contains(p) ? ""+'\u2659' : "");
    		if (p.equals(this.logics.heroPosition())) { 
    			b.setText(""+(this.logics.currentPiece() == Logics.Piece.KING ? '\u265A' : '\u265E'));
    		}
    	});
    }
    
}
