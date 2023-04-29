package ex2016.a02b.sol2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private List<JButton> buttons;
	
	public GUI(int size){
		final Agent agent = new Agent(size);
		
		JButton jbStop = new JButton("Stop");
        jbStop.addActionListener(e -> agent.stopAdvancing());
        JPanel jp = new JPanel();
        buttons = IntStream.range(0,size)
                           .mapToObj(i->new JButton(" "))
                           .peek(jb->jb.setEnabled(false))
                           .peek(jp::add)
                           .collect(Collectors.toList());
        jp.add(jbStop);
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);		
        agent.start();
	}
	
	private void viewUpdate(Set<Integer> positions){
        this.buttons.forEach(jb -> jb.setText(positions.contains(buttons.indexOf(jb))?"*":" "));
    }

	private class Agent extends Thread {

		private volatile boolean stop = false;
		private Model model;
		
		private Agent(int size){
		    this.model = new ModelImpl(size);
		}
		
		public void run() {
			while (!stop) {
			    Set<Integer> positions = model.positions();
				SwingUtilities.invokeLater(()->viewUpdate(positions));
				model.advance();
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
}
