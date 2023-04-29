package a01b.e2;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Example extends JFrame{
	
	public Example(){
		
	
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		ActionListener ac = (src)-> {
			JButton b =(JButton)src.getSource();
			b.setText("*");
			b.setEnabled(false);
		};
		JButton b1 = new JButton("?");
		JButton b2 = new JButton("?");
		b1.addActionListener(ac);
		b2.addActionListener(ac);
		this.getContentPane().add(b1);
		this.getContentPane().add(b2);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		new Example();
	}

}
