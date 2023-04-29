package a05.sol2;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.Random;


public class Example extends JFrame{
	

	public Example(){
		Random random = new Random();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		JPanel jNorth = new JPanel();
		JPanel jSouth = new JPanel();
		JPanel jCenter = new JPanel();
		this.getContentPane().add(BorderLayout.NORTH, jNorth);
		this.getContentPane().add(BorderLayout.SOUTH, jSouth);
		this.getContentPane().add(BorderLayout.CENTER, jCenter);
		JButton one = new JButton("*");
		one.setEnabled(false);
		jNorth.add(one);
		jNorth.add(new JButton("*"));
		jNorth.add(new JButton("*"));
		jSouth.add(new JButton("*"));
		jSouth.add(new JButton("*"));
		jSouth.add(new JButton("*"));
		JButton p = new JButton("P");
		jCenter.add(p);
		p.addActionListener(e ->{
			System.out.println("numero random (fra 0 e 5 inclusi): "+random.nextInt(6));
			one.setText(" ");
		});
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new Example();
	}

}

