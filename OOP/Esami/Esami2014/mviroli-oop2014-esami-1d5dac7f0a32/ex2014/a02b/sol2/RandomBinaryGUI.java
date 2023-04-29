package ex2014.a02b.sol2;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;

import javax.swing.*;

import ex2014.a02.sol2.Controller.Button;


public class RandomBinaryGUI extends JFrame{
	
	private static final long serialVersionUID = 4471071323193950459L;
	
	private Controller controller;
	private JButton dec = new JButton("<");
	private JButton inc = new JButton(">");
	private JLabel lab = new JLabel("1");
	private JButton write = new JButton("Write"); 
	private JButton quit = new JButton("Quit");
	
	
	public RandomBinaryGUI(String fileName){
		
		this.controller = new ControllerImpl(fileName);
		// Inizializzazione base
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		this.getContentPane().add(dec);
		this.getContentPane().add(inc);
		this.getContentPane().add(lab);
		this.getContentPane().add(write);
		this.getContentPane().add(quit);
		
		inc.addActionListener(e->{controller.incCount();reEnable();});
		dec.addActionListener(e->{controller.decCount();reEnable();});
		write.addActionListener(e->{controller.hitWrite();reEnable();});
		quit.addActionListener(e->{controller.hitQuit();System.exit(0);});
		
		this.reEnable();
		this.setVisible(true);
	}

	private void reEnable(){
		dec.setEnabled(controller.enableDec());
		inc.setEnabled(controller.enableInc());
		lab.setText(""+controller.getCount());
	} 
	
	public static void main(String[] args) throws Exception{
		
		String filename = System.getProperty("user.dir")+System.getProperty("file.separator")+"buttons.dat";
		System.out.println("Using file: "+filename);
		new RandomBinaryGUI(filename);
	}

}
