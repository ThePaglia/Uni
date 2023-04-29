package ex2015.a02a.sol2;

import javax.swing.*;
import java.util.List;
import java.util.stream.*;
import java.io.*;
import java.nio.file.*;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final JLabel jDisplay = new JLabel();
    private final JButton jStop = new JButton("stop");
    private final JButton jMark = new JButton("mark");
    private final Model model;
    private final Agent agent = new Agent();
    
    public GUI(String fileName) throws IOException {
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 100);

        this.model = new Model(fileName);
        JPanel panel = new JPanel();
        panel.add(jDisplay);
        panel.add(jMark);
        panel.add(jStop);
        
        jStop.addActionListener(e -> {
            agent.stopCounting();
            jStop.setEnabled(false);
            jMark.setEnabled(false);
        });
        jMark.addActionListener(e -> {
            this.model.mark();
            this.updateDisplay();
        });

        this.getContentPane().add(panel);
        this.updateDisplay();
        this.setVisible(true);
        agent.start();
    }
    
    private void updateDisplay(){
        this.jDisplay.setText(this.model.getCurrent());
    }
    
    
    private class Model {
        private int pos = 0;
        private List<String> list;
        
        public Model(String fileName) throws IOException {
            this.list = Files.lines(FileSystems.getDefault().getPath(fileName)).collect(Collectors.toList());
        }
        
        public String getCurrent(){
            return list.get(pos);
        }
        
        public void next(){
            pos = (pos + 1) % list.size();
        }
        
        public void mark(){
            list.set(pos, list.get(pos)+"*");
        }
    }

    private class Agent extends Thread {

        private volatile boolean stop = false;
        
        public void run() {
            while (!stop) {
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                }
                SwingUtilities.invokeLater(() -> {
                    model.next();
                    updateDisplay();
                });
            }
        }

        public void stopCounting() {
            this.stop = true;
        }
    }
}
