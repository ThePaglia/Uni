package a01b.sol2;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import javax.swing.*;

public class GUI extends JFrame{
	
	private static final long serialVersionUID = 4471071323193950459L;
	
	private Logics controller;
	private List<JButton> blist = null;
	private int counter = 0;
	
	
	public GUI(int size){
		
		this.controller = new LogicsImpl();
		this.controller.reset(size, 100);

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
		};
		
		this.blist = IntStream.range(0, size).mapToObj((i)->new JButton("*")).collect(Collectors.toList());
		this.blist.stream().forEach((b)->{
			b.addActionListener(ac);
			this.getContentPane().add(b);
		});
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws Exception{
		new GUI(5);
	}

}
