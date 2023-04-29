package ex2016.a03b.sol2;

import java.lang.reflect.InvocationTargetException;
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
		
		JPanel jp = new JPanel();
        buttons = IntStream.range(0,size)
                           .mapToObj(i->new JButton("  "))
                           .peek(jb->jb.setEnabled(false))
                           .peek(jp::add)                           
                           .collect(Collectors.toList());
        buttons.get(0).setEnabled(true);
        buttons.get(0).addActionListener(e -> {
            agent.hit(); 
            ((JButton)e.getSource()).setEnabled(false);}
        );
        JFrame jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);		
        agent.start();
	}
	
	private void viewUpdate(int position){
        this.buttons.forEach(jb -> jb.setText("  "));
        this.buttons.get(position).setText("X");
    }

	private class Agent extends Thread {

		private volatile boolean hit = false;
		private Model model;
		
		private Agent(int size){
		    this.model = new ModelImpl(size);
		}
		
		public void run() {
			while (true) {
			    int position = model.getPosition();
			    if (hit && position==0){
			        System.exit(0);
			    }
				try {
                    SwingUtilities.invokeAndWait(()->viewUpdate(position));
                } catch (InvocationTargetException | InterruptedException e) {
                    e.printStackTrace();
                }
				model.advance();
				try {
					Thread.sleep(300);
				} catch (Exception ex) {
				}
			}
		}

		public void hit() {
			this.hit = true;
		}
	}
}
