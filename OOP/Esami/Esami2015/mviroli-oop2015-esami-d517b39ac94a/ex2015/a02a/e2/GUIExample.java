package ex2015.a02a.e2;

import javax.swing.*;

public class GUIExample extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final JLabel jDisplay = new JLabel();
    private final JButton jStop = new JButton("stop");
    private final Agent agent = new Agent();

    public GUIExample() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 100);

        JPanel panel = new JPanel();
        panel.add(jDisplay);
        panel.add(jStop);

        jStop.addActionListener(e -> agent.stopCounting());

        this.getContentPane().add(panel);
        this.setVisible(true);
        this.agent.start();
    }

    private class Agent extends Thread {

        private volatile boolean stop = false;
        private int counter = 0;

        public void run() {
            while (!stop) {
                SwingUtilities.invokeLater(() -> {
                    jDisplay.setText("" + counter);
                    counter++;
                });
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }
        }

        public void stopCounting() {
            this.stop = true;
        }
    }
}
