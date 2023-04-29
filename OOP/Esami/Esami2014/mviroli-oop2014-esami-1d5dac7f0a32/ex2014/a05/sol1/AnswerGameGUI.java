package ex2014.a05.sol1;

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

	private static final int MIN = 1;
	private static final int MAX = 100;
	private final JFrame gui = new JFrame();
	private GuessGame game = new GuessGameImpl(MIN, MAX);

	/**
	 * Builds a new {@link AnswerGameGUI}.
	 */
	public AnswerGameGUI() {
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		final JPanel upper = new JPanel();
		upper.setLayout(new BoxLayout(upper, BoxLayout.X_AXIS));
		upper.add(new JLabel("Your guess: "));
		final JTextField txt = new JTextField(
				(int) Math.ceil(Math.log(MAX)) + 2);
		txt.setText(Integer.toString((MAX - MIN) / 2 + MIN));
		upper.add(txt);
		final JButton guessbt = new JButton("Guess!");
		upper.add(guessbt);
		main.add(upper);
		final JPanel lower = new JPanel();
		lower.setLayout(new BoxLayout(lower, BoxLayout.X_AXIS));
		final JLabel status = new JLabel("No guess yet.");
		lower.add(status);
		main.add(lower);

		guessbt.addActionListener(e -> {
			try {
				final int v = Integer.parseInt(txt.getText());
				final Answer ans = game.guess(v);
				switch (ans) {
				case CORRECT:
					status.setText("You won in " + game.getGuesses() + " attempts.");
					txt.setEnabled(false);
					guessbt.setEnabled(false);
					break;
				case TOO_HIGH:
					status.setText("You guessed to high.");
					break;
				case TOO_LOW:
					status.setText("You guessed too low.");
					break;
				default:
					throw new IllegalStateException("There is a bug here.");
				}
				if (ans!=Answer.CORRECT && game.isFinished()){
					System.out.println("You lost!");
					System.exit(0);
				}
			} catch (NumberFormatException ex) {
				status.setText("Wrong number.");
			}
		});

		gui.getContentPane().add(main);
		gui.pack();
		gui.setVisible(true);
	}

	/**
	 * Executes a program which creates a new {@link AnswerGameGUI} and shows it
	 * on screen.
	 * 
	 * @param a
	 *            ignored
	 */
	public static void main(final String... a) {
		final AnswerGameGUI ui = new AnswerGameGUI();
	}

}
