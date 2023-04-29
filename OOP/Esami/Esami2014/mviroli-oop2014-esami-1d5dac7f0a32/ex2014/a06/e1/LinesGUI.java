package ex2014.a06.e1;

import java.io.*;
import java.awt.*;
import javax.swing.*;

public class LinesGUI {

	private final JFrame gui = new JFrame();
	private final JButton down = new JButton("Down");
	private final JButton exit = new JButton("Exit");
		
	public LinesGUI(String fileName) throws IOException {
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(down);
		main.add(exit);
		this.exit.addActionListener(e -> System.exit(0));
		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}

}
