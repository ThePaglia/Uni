package ex2015.a02b.sol2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.stream.*;

public class GUI extends JFrame {

    private static final long serialVersionUID = -5706946455871549922L;

    public GUI() {
        // Inizializzazione base
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        final Model model = new ModelImpl();

        final ActionListener valListener = e -> {
            JButton jb = (JButton) e.getSource();
            jb.setEnabled(false);
            try {
                Model.Result res = model.numberDrawn(Integer.parseInt(jb.getText()));
                if (res == Model.Result.TOMBOLINA) {
                    JOptionPane.showMessageDialog(this, "TOMBOLINA");
                    System.exit(0);
                }
                if (res == Model.Result.AMBO) {
                    JOptionPane.showMessageDialog(this, "AMBO");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERRORE: " + ex.getMessage());
            }
        };

        // Pannello centrale
        JPanel center = new JPanel(new GridLayout(2, 5));
        List<JButton> buttonValues = model.getValues().stream().map(String::valueOf).map(JButton::new)
                .collect(Collectors.toList());
        buttonValues.forEach(b -> {
            b.addActionListener(valListener);
            center.add(b);
        });

        this.getContentPane().add(BorderLayout.CENTER, center);
        this.setVisible(true);
    }

}
