package a06.sol2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final List<JButton> cells = new LinkedList<>();
	private final JButton next = new JButton(">");
	private final Logics logics;
	
	public GUI(int rows) {
		this.logics = new LogicsImpl(rows);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(100 * rows, 100);

		JPanel panel = new JPanel(new GridLayout(1, rows));
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(next, BorderLayout.SOUTH);

		next.addActionListener(e -> {
			logics.next();
			updateView();
			if (logics.isOver()) {
				System.exit(0);
			}
		});

		for (int j = 0; j < rows; j++) {
			final JButton jb = new JButton(" ");
			this.cells.add(jb);
			panel.add(jb);
		}
		this.updateView();
		this.setVisible(true);
	}

	private void updateView() {
		var positions = logics.getNumbers();
		System.out.println(positions);
		for (int i=0; i<this.cells.size();i++) {
			cells.get(i).setText(positions.get(i) == 0 ? "" : String.valueOf(positions.get(i)));
		};
		cells.get(logics.getItemPosition()).setText("*");
	}

}
