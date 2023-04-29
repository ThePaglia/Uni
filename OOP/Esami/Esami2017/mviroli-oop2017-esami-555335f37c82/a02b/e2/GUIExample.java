package a02b.e2;

import java.awt.*;
import javax.swing.*;

public class GUIExample {

	public GUIExample(){
		final JFrame gui = new JFrame();
		final JComboBox<String> combo = new JComboBox<>();
		final JButton remove = new JButton("Remove");
		combo.removeAllItems();
		combo.addItem("a");
		combo.addItem("b");
		combo.setSelectedIndex(0);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(combo);
		main.add(remove);
		remove.addActionListener(e -> {
			System.out.println(combo.getSelectedIndex());
		});
		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUIExample();
	}

}
