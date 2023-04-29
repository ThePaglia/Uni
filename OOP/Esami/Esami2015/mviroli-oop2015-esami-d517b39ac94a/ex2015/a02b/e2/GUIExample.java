package ex2015.a02b.e2;

import java.awt.*;
import javax.swing.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class GUIExample extends JFrame {

    private static final long serialVersionUID = -8854543282432946255L;

    public GUIExample() {
        // Inizializzazione base
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        List<Integer> l = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80);

        // Pannello centrale
        JPanel center = new JPanel(new GridLayout(2, 4));
        // Creo una lista di pulsanti a partire da l
        List<JButton> buttonValues = l.stream().map(String::valueOf).map(JButton::new).collect(Collectors.toList());
        // Per ogni pulsante, lo aggiungo al panel e gli imposto un (diverso) oggetto listener
        buttonValues.forEach(b -> {
            center.add(b);
            b.addActionListener(e -> JOptionPane.showMessageDialog(GUIExample.this,
                    "pressed " + ((JButton) e.getSource()).getText()));
        });

        this.getContentPane().add(BorderLayout.CENTER, center);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new GUIExample();
    }

}
