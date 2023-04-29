package ex2014.a04.sol2;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import javax.swing.*;

public class FindMaxGUI extends JFrame{
	
	private static final long serialVersionUID = 4471071323193950459L;
	
	private Controller controller;
	private List<JButton> blist = null;
	private int counter = 0;
	
	
	public FindMaxGUI(int size, int max){
		
		this.controller = new ControllerImpl();
		this.controller.reset(size, max);

		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		ActionListener ac = (src)-> {
			JButton b =(JButton)src.getSource();
			int pos = blist.indexOf(b);
			Optional<Integer> found = this.controller.tryNumber(pos);
			this.counter++;
			System.out.println("Attempt: "+counter);
			if (found.isPresent()){
				b.setText(String.valueOf(found.get()));
				b.setEnabled(false);
			}
			if (this.controller.allFound()){
				System.exit(0);
			}
		};
		
		this.blist = IntStream.range(0, size).mapToObj((i)->new JButton("?")).collect(Collectors.toList());
		this.blist.stream().forEach((b)->{
			b.addActionListener(ac);
			this.getContentPane().add(b);
		});
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws Exception{
		new FindMaxGUI(8,100);
	}

}
