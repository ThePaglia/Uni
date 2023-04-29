package a02b.sol2;

import java.io.*;
import java.awt.*;
import javax.swing.*;

public class GUI {

	private final JFrame gui = new JFrame();
	private final JComboBox<String> combo = new JComboBox<>();
	private final JButton remove = new JButton("Remove");
	private final JButton concat = new JButton("Concat");
	private final JButton add = new JButton("Add");
	private final Logics logics;
	
	private void resetCombo() {
		this.combo.removeAllItems();
		logics.allLines().forEach(this.combo::addItem);
		this.combo.setSelectedIndex(0);
		gui.pack();
	}
	
	public GUI(String fileName) throws IOException {
		this.logics = new LogicsImpl(fileName);
		this.resetCombo();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(combo);
		main.add(remove);
		main.add(concat);
		main.add(add);
		this.remove.addActionListener(e -> {
			this.logics.remove(this.combo.getSelectedIndex());
			resetCombo();
		});
		this.concat.addActionListener(e -> {
			this.logics.concat(this.combo.getSelectedIndex());
			resetCombo();
		});
		this.add.addActionListener(e -> {
			this.logics.add(this.combo.getSelectedIndex());
			resetCombo();
		});
		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}

}
