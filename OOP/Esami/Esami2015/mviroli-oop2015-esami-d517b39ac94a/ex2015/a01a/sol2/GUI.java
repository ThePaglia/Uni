package ex2015.a01a.sol2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.stream.*;


public class GUI extends JFrame{
	
	public GUI(){
		// Inizializzazione base
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		final Model model = new ModelImpl();
		
		final ActionListener valListener = e -> {
			JButton jb = (JButton)e.getSource();
			jb.setEnabled(false);
			model.hitValue(jb.getText());
		};
		
		// Pannello centrale
		JPanel center = new JPanel(new FlowLayout());
		List<JButton> buttonValues = model.getValues().stream().map(JButton::new).collect(Collectors.toList());
		buttonValues.forEach(b->{
			b.addActionListener(valListener);
			center.add(b);
		});
		
		final ActionListener opListener = e -> {
			String s = ((JButton)e.getSource()).getText();
			JOptionPane.showMessageDialog(this, "The result is: "+model.execOperation(s));
			model.resetValues();
			buttonValues.forEach(b->b.setEnabled(true));
		};
		
		// Pannello sud, ossia in basso
		final JPanel south = new JPanel(new FlowLayout());
		model.getOperations().forEach(s -> {
			JButton jb = new JButton(s);
			jb.addActionListener(opListener);
			south.add(jb);
		});
		this.getContentPane().add(BorderLayout.SOUTH,south);
		this.getContentPane().add(BorderLayout.CENTER,center);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		new GUI();
	}

}
