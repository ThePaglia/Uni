package ex2015.a01b.sol2;

import java.awt.*;
import java.util.*;
import java.util.stream.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Example class.
 */
public class GUI extends JFrame {

    public GUI() {
        super();
        final Strategy strategy = new StrategyImpl();
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());

        final JPanel center = new JPanel(new FlowLayout());
        strategy.getInputs().forEach(input -> {
            final JButton btn = new JButton(input);
            btn.addActionListener(e -> {
                btn.setEnabled(false);
                strategy.hitString(input);
            });
            center.add(btn);
        });

        final JPanel south = new JPanel(new FlowLayout());
        strategy.getOperations().forEach(op -> {
            final JButton btn = new JButton(op);
            btn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "The result is " + strategy.doOperation(op));
                strategy.reset();
                Arrays.stream(center.getComponents()).filter(c -> c instanceof JButton).map(c -> (JButton) c)
                        .forEach(b -> b.setEnabled(true));
            });
            south.add(btn);
        });
        canvas.add(BorderLayout.SOUTH, south);

        canvas.add(BorderLayout.CENTER, center);
        setLocationByPlatform(true);
        setContentPane(canvas);
        this.setVisible(true);
    }

}
