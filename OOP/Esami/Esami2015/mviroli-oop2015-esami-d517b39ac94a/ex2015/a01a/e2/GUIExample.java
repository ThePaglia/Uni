package ex2015.a01a.e2;

import java.awt.*;


import javax.swing.*;


public class GUIExample extends JFrame{

	public GUIExample(){
		// Inizializzazione base
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		
		// Pannello sud, ossia in basso
		final JPanel south = new JPanel(new FlowLayout());
		final JButton sum = new JButton("SUM");
		final JButton mult = new JButton("MULTIPLY");
		final JButton min = new JButton("CONCAT");
		south.add(sum);
		south.add(mult);
		south.add(min);
		this.getContentPane().add(BorderLayout.SOUTH,south);
		
		sum.addActionListener(e -> JOptionPane.showMessageDialog(this, "The result is ..sum.."));
		mult.addActionListener(e -> JOptionPane.showMessageDialog(this, "The result is ..mult.."));
		min.addActionListener(e -> JOptionPane.showMessageDialog(this, "The result is ..min.."));
		
		// Pannello centrale
		JPanel center = new JPanel(new FlowLayout());
		for(int i = 0; i < 100; i++){
			center.add(new JButton(String.valueOf(i)));
		}
		this.getContentPane().add(BorderLayout.CENTER,center);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		new GUIExample();
	}

}
