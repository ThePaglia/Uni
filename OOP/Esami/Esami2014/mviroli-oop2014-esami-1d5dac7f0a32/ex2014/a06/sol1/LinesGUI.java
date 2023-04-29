package ex2014.a06.sol1;

import java.io.*;
import java.awt.*;
import javax.swing.*;

public class LinesGUI {

	private final JFrame gui = new JFrame();
	private Lines lines = null;
	private final JTextField txt = new JTextField(80);
	private final JButton up = new JButton("Up");
	private final JButton down = new JButton("Down");
	private final JButton exit = new JButton("Exit");
	
	private void update(){
		this.up.setEnabled(!this.lines.isTop());
		this.down.setEnabled(!this.lines.isBottom());
		if (!this.lines.isEmpty()){
			this.txt.setText(this.lines.getCurrentString().get());
		}
	}
	
	/**
	 * Builds a new {@link LinesGUI}.
	 */
	public LinesGUI(String fileName) throws IOException {
		this.lines = new LinesFromFile(fileName);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(txt);
		main.add(up);
		main.add(down);
		main.add(exit);
		this.update();
		this.up.addActionListener(e -> {
			this.lines.goUp();
			this.update();
		});
		this.down.addActionListener(e -> {
			this.lines.goDown();
			this.update();
		});
		this.exit.addActionListener(e -> System.exit(0));
		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}

	public static void main(String[] a) throws IOException {
		System.out.println("Looking at file "+System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");	
		final LinesGUI ui = new LinesGUI(System.getProperty("user.home")+System.getProperty("file.separator")+"a.txt");
	}

}
