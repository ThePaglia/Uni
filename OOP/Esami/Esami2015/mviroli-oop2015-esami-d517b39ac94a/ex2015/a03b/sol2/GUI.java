package ex2015.a03b.sol2;

import javax.swing.*;

import java.util.List;
import java.util.stream.*;
import java.awt.BorderLayout;
import java.io.*;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private static int NLINES = 5;
    private static int MAXLINESIZE = 20;
    
    private final List<JLabel> labelList;
    private final JButton jNext = new JButton("Next");
    private final JButton jPrev = new JButton("Prev");
    private final Model model;
    
    public GUI(String fileName) throws IOException {
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200, 200);
        
        this.model = new ModelImpl(fileName,NLINES);
        this.labelList = IntStream.range(0, NLINES).mapToObj(i->new JLabel("",SwingConstants.LEFT)).collect(Collectors.toList());
        this.updatePanel();
        
        Box panel = Box.createVerticalBox();
        this.labelList.forEach(panel::add);
        
        this.jNext.addActionListener(e -> {
            model.next();
            updatePanel();
        });
        
        this.jPrev.addActionListener(e -> {
            model.prev();
            updatePanel();
        });
        
        this.getContentPane().add(BorderLayout.CENTER,panel);
        JPanel south = new JPanel();
        south.add(jPrev);
        south.add(jNext);
        this.getContentPane().add(BorderLayout.SOUTH,south);
        this.setVisible(true);
    }
    
    private void updatePanel(){
        List<String> ls = this.model.getCurrentLines();
        for (int i=0; i<this.labelList.size(); i++){
            this.labelList.get(i).setText(i<ls.size() ? ls.get(i).substring(0, MAXLINESIZE) : "");
        }
        this.jNext.setEnabled(this.model.hasNext());
        this.jPrev.setEnabled(this.model.hasPrev());
    }
        
}
