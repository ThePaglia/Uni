package a03a.sol2;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI {

	private final JFrame gui = new JFrame();
	private List<JButton> buttons;
	private final Logics logics;
	
	public GUI(String fileName) throws IOException {
		this.logics = new LogicsImpl(fileName);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		ActionListener al = e -> {
			JButton jb = (JButton)e.getSource();
			jb.setEnabled(false);
			logics.disable(this.buttons.indexOf(jb));
		};
		this.buttons = this.logics.allLines()
						   .stream()
						   .map(JButton::new)
						   .peek(main::add)
						   .peek(jb->jb.addActionListener(al))
						   .collect(Collectors.toList());
		final JScrollPane scroll = new JScrollPane(main);
		final JPanel outer = new JPanel(new BorderLayout());
		final JPanel south = new JPanel();
		outer.add(scroll,BorderLayout.CENTER);
		outer.add(south,BorderLayout.SOUTH);
		JButton close = new JButton("Close");
		close.addActionListener(e -> {
			logics.closeAndWrite();
			System.exit(0);
		});
		south.add(close);
		gui.getContentPane().add(outer);
		gui.setSize(600,300);
		gui.setVisible(true);
	}

}
