package ex2016.a03b.e2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.*;

public class GUI extends JFrame {

    private JButton jbValue1;
    private JButton jbValue2;
    
    
	public GUI(int size){
		final Agent agent = new Agent();
		
		jbValue1 = new JButton("0");
		jbValue2 = new JButton("0");
		JPanel jp = new JPanel();
        jp.add(jbValue1);
        jp.add(jbValue2);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);		
        agent.start();
	}
	
	private class Agent extends Thread {

		private volatile boolean stop = false;
		private int counter; // the mode here is just a counter!
		
		public void run() {
			while (!stop) {
			    counter++;
			    String s = ""+counter; // creating a local copy
			    SwingUtilities.invokeLater(()->{jbValue1.setText(s); jbValue2.setText(s);});
				try {
					Thread.sleep(300);
				} catch (Exception ex) {
				}
			}
		}

		public void stopAdvancing() {
			this.stop = true;
		}
	}
	
	public static void main(String[] s){
	    new GUI(10);
	}
}
