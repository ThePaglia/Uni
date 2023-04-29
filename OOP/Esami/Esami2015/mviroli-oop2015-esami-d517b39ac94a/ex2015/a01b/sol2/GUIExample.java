package ex2015.a01b.sol2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Example class.
 */
public class GUIExample extends JFrame {

    private static final int BUTTONS = 100;
    
    public GUIExample() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());

        // Pannello sud, ossia in basso
        final JPanel south = new JPanel(new FlowLayout());
        final JButton lng = new JButton("OF LENGTH 4");
        final JButton sma = new JButton("< 1000");
        final JButton trap = new JButton("WITH TRAILING P");
        south.add(lng);
        south.add(sma);
        south.add(trap);
        canvas.add(BorderLayout.SOUTH, south);

        lng.addActionListener(e -> JOptionPane.showMessageDialog(this, "The result is ..."));
        sma.addActionListener(e -> JOptionPane.showMessageDialog(this, "The result is ..."));
        trap.addActionListener(e -> JOptionPane.showMessageDialog(this, "The result is ..."));

        // Pannello centrale
        JPanel center = new JPanel(new FlowLayout());
		for (int i = 0; i < BUTTONS; i++) {
           center.add(new JButton(i + "aa"));
        }
        canvas.add(BorderLayout.CENTER, center);
        setLocationByPlatform(true);
        setContentPane(canvas);
        this.setVisible(true);
    }
    
    public static void main(String[] s){
    	new GUIExample();
    }

}
