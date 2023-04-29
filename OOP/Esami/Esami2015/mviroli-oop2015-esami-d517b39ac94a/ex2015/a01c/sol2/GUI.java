package ex2015.a01c.sol2;

import java.io.*;
import java.awt.*;
import javax.swing.*;

public class GUI {

	private final JFrame gui = new JFrame();
	private final JTextField num = new JTextField(20);
	private final JButton add = new JButton("Add");
	private final JButton save = new JButton("Save");
	private final JButton show = new JButton("Show");
	private final Controller controller;
	
	private void updateButtons(){
		this.save.setEnabled(this.controller.canSave());
		this.show.setEnabled(this.controller.canShow());
	}
	
	/**
	 * Builds a new {@link GUI}.
	 */
	public GUI(String fileName) throws IOException {
		this.controller = new ControllerOnFile(fileName);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(num);
		main.add(add);
		main.add(save);
		main.add(show);
		this.updateButtons();
		this.add.addActionListener(e -> {
			try{
				this.controller.add(Integer.parseInt(num.getText()));
				this.updateButtons();
			} catch (Exception ex){
				System.out.println("Number incorrect.. nothing is added");
			}
		});
		this.save.addActionListener(e -> {
			try{
				this.controller.save();
				this.updateButtons();
			} catch (Exception ex){
				System.out.println("Could not save!");
			}
		});
		this.show.addActionListener(e -> {
			try{
				this.controller.show();
				this.updateButtons();
			} catch (Exception ex){
				System.out.println("Could not show!");
			}
		});
		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}

	public static void main(String[] a) throws IOException {
		System.out.println("Looking at file "+System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");	
		final GUI ui = new GUI(System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");
	}

}
