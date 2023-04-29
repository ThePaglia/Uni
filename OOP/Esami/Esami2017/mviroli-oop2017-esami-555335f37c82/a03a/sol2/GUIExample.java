package a03a.sol2;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUIExample {

	private final JFrame gui = new JFrame();
	
	public GUIExample(){
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(new JButton("a"));
		main.add(new JButton("b"));
		final JPanel outer = new JPanel(new BorderLayout());
		final JPanel center = main;
		outer.add(main,BorderLayout.CENTER);
		JButton close = new JButton("Close");
		close.addActionListener(e -> ((JButton)e.getSource()).setEnabled(false));
		outer.add(close,BorderLayout.EAST); // nell'esame il pulsante Close va messo però in basso, e possibilmente delle giuste dimensioni
		gui.getContentPane().add(outer);
		gui.setSize(600,300);
		gui.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUIExample();
	}

}
