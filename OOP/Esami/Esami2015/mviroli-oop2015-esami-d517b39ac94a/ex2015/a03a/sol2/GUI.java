package ex2015.a03a.sol2;

import javax.swing.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.*;

public class GUI extends JFrame {
    
    private static final String[] CHOICES = {"Don't know", "Yes", "No"};
    private static final List<Optional<Boolean>> BOOLS = Arrays.asList(Optional.empty(), Optional.of(true), Optional.of(false));
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JComboBox<String>> questions;
    private final JButton jSubmit = new JButton("Submit");
    private final Model model;
    
    public GUI(String fileName) throws IOException {
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        
        this.model = new ModelImpl(fileName);
        final List<String> strings = model.getQuestions();
        JPanel panel = new JPanel(new GridLayout(strings.size(),2));
        
        this.questions = strings.stream()
                                .peek(s->panel.add(flowBoxed(new JLabel(s))))
                                .map((s)->new JComboBox<>(CHOICES))
                                .peek(jc->panel.add(flowBoxed(jc)))
                                .collect(Collectors.toList());
        
        jSubmit.addActionListener(
                e->System.out.println(this.model.getScore(this.questions
                                                              .stream()
                                                              .map(JComboBox::getSelectedIndex)
                                                              .map(BOOLS::get)
                                                              .collect(Collectors.toList()))));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        JPanel south = new JPanel();
        south.add(jSubmit);
        this.getContentPane().add(BorderLayout.SOUTH,south);
        this.setVisible(true);
    }
    
    private JPanel flowBoxed(JComponent jc){
        JPanel jp = new JPanel(new FlowLayout());
        jp.add(jc);
        return jp;
    }
        
}
