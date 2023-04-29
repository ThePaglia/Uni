package ex2014.a02.sol2;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;

import javax.swing.*;

import ex2014.a02.sol2.Controller.Button;


public class CombinationGUI extends JFrame{
	
	private static final long serialVersionUID = 4471071323193950459L;
	
	private Controller controller;
	private EnumMap<Controller.Button,JButton> map = new EnumMap<>(Controller.Button.class);
	private JButton write = new JButton("Write"); 
	private JButton quit = new JButton("Quit");
	
	
	public CombinationGUI(String fileName){
		
		this.controller = new ControllerImpl(fileName);

		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		for (Controller.Button b: Controller.Button.values()){
			JButton jb = new JButton(b.name());
			this.getContentPane().add(jb);
			this.map.put(b, jb);
			jb.addActionListener(e->{controller.hit(b);reEnable();});
		}
		this.getContentPane().add(write);
		write.addActionListener(e->{controller.hitWrite();reEnable();});
		this.getContentPane().add(quit);
		quit.addActionListener(e->{controller.hitQuit();System.exit(0);});
		
		this.reEnable();
		this.setVisible(true);
	}

	private void reEnable(){
		this.map.forEach((b,jb)->jb.setEnabled(controller.enabled(b)));
		write.setEnabled(controller.enabledWrite());
		quit.setEnabled(controller.enabledQuit());
	} 
	
	public static void main(String[] args) throws Exception{
		
		String filename = System.getProperty("user.dir")+System.getProperty("file.separator")+"buttons.dat";
		System.out.println("Using file: "+filename);
		new CombinationGUI(filename);
	}

}
