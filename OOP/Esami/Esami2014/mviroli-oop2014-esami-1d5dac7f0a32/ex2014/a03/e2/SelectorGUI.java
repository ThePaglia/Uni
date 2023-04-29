package ex2014.a03.e2;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SelectorGUI extends JFrame{

	
	public SelectorGUI(SelectorModel cs){
		// Inizializzazione base
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
					
		this.setVisible(true);
	}


}
