package ex2014.a01.e2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class FormGUI extends JFrame{

	private JTextField nome = new JTextField(20);
	private JTextField cognome = new JTextField(20);
	private JTextField cf = new JTextField(20);
	
	public FormGUI(){
		// Inizializzazione base
		this.setSize(500, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		
		// Pannello sud, ossia in basso
		JPanel south = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		south.add(ok);
		this.getContentPane().add(BorderLayout.SOUTH,south);
		
		// Pannello centrale, ossia una griglia a due colonne
		JPanel center = new JPanel(new GridLayout(0,2));
		center.add(wrapperPanel(new JLabel("Nome"),FlowLayout.RIGHT));
		center.add(wrapperPanel(this.nome,FlowLayout.CENTER));
		center.add(wrapperPanel(new JLabel("Cognome"),FlowLayout.RIGHT));
		center.add(wrapperPanel(this.cognome,FlowLayout.CENTER));
		center.add(wrapperPanel(new JLabel("CF"),FlowLayout.RIGHT));
		center.add(wrapperPanel(this.cf,FlowLayout.CENTER));
		this.getContentPane().add(BorderLayout.CENTER,center);
		
		this.setVisible(true);
		
		// Handler pulsante ok
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nome.getText().length() != 0 && 	// nome non vuoto
					cognome.getText().length() != 0 &&  // cognome non vuoto
					cf.getText().length() == 16 &&		// cf di 16 caratteri
					cognome.getText().charAt(0)==cf.getText().charAt(0)){ // primo carattere di cognome e cf identici
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(FormGUI.this, "Empty names or surname, or incorrect CF", "Incorrect data!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}

	/*
	 *  Helper function statica per wrappare un componente in un pannellino con FlowLayout
	 *  Serve a garantire che il componente sia piazzato secondo le sue dimentioni preferite
	 */
	private static JPanel wrapperPanel(final JComponent component, final int orientation){
		final JPanel panel = new JPanel(new FlowLayout(orientation));
		panel.add(component);
		return  panel;
	}
	
	public static void main(String[] args){
		new FormGUI();
	}

}
