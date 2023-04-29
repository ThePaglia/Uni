package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final List<JButton> cells = new LinkedList<>();
	private final JButton next = new JButton(">");
	private final Random random = new Random();
	
	public GUI(int rows) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(5 * 100, 100);

		JPanel panel = new JPanel(new GridLayout(1, rows));
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(next, BorderLayout.SOUTH);

		next.addActionListener(e -> {
			cells.get(0).setText(String.valueOf(random.nextInt(10)+1));
			cells.get(1).setText("*");
		});

		for (int j = 0; j < rows; j++) {
			final JButton jb = new JButton(" ");
			this.cells.add(jb);
			panel.add(jb);
		}
		this.setVisible(true);
	}
}
