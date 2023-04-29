package ex2014.a04.sol2_java7;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class FindMaxGUI extends JFrame{
	
	private static final long serialVersionUID = 4471071323193950459L;
	
	private final Controller controller;
	private final List<JButton> blist;
	private int counter = 0;
	
	
	public FindMaxGUI(final int size, final int max){
		
		controller = new ControllerImpl();
		controller.reset(size, max);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		final JPanel pan = new JPanel();
		getContentPane().add(pan);
		pan.setLayout(new FlowLayout());
		
		ActionListener ac = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent src) {
				/*
				 * Never cast without a proper check.
				 */
				if (src.getSource() instanceof JButton) {
					final JButton b = (JButton) src.getSource();
					final Integer found = controller.tryNumber(blist.indexOf(b));
					counter++;
					System.out.println("Attempt: "+counter);
					if (found != null){
						b.setText(Integer.toString(found));
						b.setEnabled(false);
						pack();
					}
					if (controller.allFound()){
						System.exit(0);
					}
				}
			}
		};
		
		blist = new ArrayList<>(size);
		for (int i = 0; i< size; i++) {
			final JButton bt = new JButton("?");
			blist.add(bt);
			bt.addActionListener(ac);
			pan.add(bt);
		}
		pack();
		setVisible(true);
	}
	
	public static void main(final String... args) throws Exception{
		new FindMaxGUI(8,100);
	}

}
