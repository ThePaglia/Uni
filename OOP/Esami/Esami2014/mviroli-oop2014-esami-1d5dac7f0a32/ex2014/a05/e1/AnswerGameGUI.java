package ex2014.a05.e1;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Danilo Pianini
 *
 */
public class AnswerGameGUI {

	private final JFrame gui = new JFrame();

	/**
	 * Builds a new {@link AnswerGameGUI}.
	 */
	public AnswerGameGUI() {
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		final JPanel upper = new JPanel();
		upper.setLayout(new BoxLayout(upper, BoxLayout.X_AXIS));
		upper.add(new JButton("..1.."));
		upper.add(new JButton("..2.."));
		upper.add(new JButton("..3.."));
		main.add(upper);
		final JPanel lower = new JPanel();
		lower.setLayout(new BoxLayout(lower, BoxLayout.X_AXIS));
		final JButton status = new JButton("......4......");
		lower.add(status);
		main.add(lower);

		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}

	

}
