package ex2014.a03b.e2;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class CodeGUI extends JFrame{

	
	public CodeGUI(CodeModel cs){
		// Inizializzazione base
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
			
		this.setVisible(true);
	}

}
