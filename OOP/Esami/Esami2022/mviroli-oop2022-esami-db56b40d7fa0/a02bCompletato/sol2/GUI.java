package a02b.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private Logic logic;
    private boolean toInit = false;
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel grid = new JPanel(new GridLayout(size,size));
        main.add(BorderLayout.CENTER, grid);
        JButton check = new JButton("Check > Restart");
        main.add(BorderLayout.SOUTH, check);
        this.getContentPane().add(main);

        check.addActionListener(e -> {
            if (toInit){
                this.logic.init();
                this.clear();
                toInit = false;
            } else {
                var res = this.logic.checkThree();
                System.out.println(res);
                res.ifPresent(set -> {
                    this.disable(set);
                    toInit = true;
                });
            }
        });
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                var position = cells.get(button);
                button.setText(logic.hit(position.getX(), position.getY()) ? "*" : " ");
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                jb.addActionListener(al);
                grid.add(jb);
            }
        }
        this.setVisible(true);
    }   
    
    private void clear() {
        this.cells.keySet().forEach(b -> {
            b.setText(" ");
            b.setEnabled(true);
        });
    }

    private void disable(Set<Pair<Integer, Integer>> set) {
        for (var entry: cells.entrySet()){
            if (set.contains(entry.getValue())){
                entry.getKey().setEnabled(false);
            }
        }
    }
}
