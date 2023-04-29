package ex2014.a03b.sol2;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.stream.*;

import javax.swing.*;


public class CodeGUI extends JFrame{

	private static final long serialVersionUID = -6475496835025520270L;
	
	
	
	public CodeGUI(CodeModel cs){
		// Inizializzazione base
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		final java.util.List<JButton> jbs = cs.currentNames()
				  .stream()
				  .map(JButton::new)
				  .collect(Collectors.toList());
		ActionListener ac = e -> {
			final JButton jb = (JButton)e.getSource();
			final int pos = jbs.indexOf(jb);
			jb.setText(cs.hit(pos));
			jb.setEnabled(cs.currentEnabling().get(pos));
		};
		jbs.forEach(x->{
			x.addActionListener(ac);
			this.getContentPane().add(x);
		});
		final JButton ok = new JButton("OK");
		this.getContentPane().add(ok);
		ok.addActionListener(x->{
			System.out.println(cs.result());
			System.exit(0);
		});
		
		this.setVisible(true);
	}

}
