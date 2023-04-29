package ex2014.a03.sol2;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.stream.*;

import javax.swing.*;


public class SelectorGUI extends JFrame{

	private static final long serialVersionUID = 577573167080015571L;
	
	public SelectorGUI(SelectorModel cs){
		// Inizializzazione base
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		final java.util.List<JButton> jbs =	cs.currentNames()
											  .stream()
											  .map(JButton::new)
											  .collect(Collectors.toList());
		
		ActionListener ac = e -> {
			final JButton jb = (JButton)e.getSource();
			final int pos = jbs.indexOf(jb);
			cs.hit(pos);
			final java.util.List<String> names = cs.currentNames();
			final java.util.List<Boolean> flags = cs.currentEnabling();
			for (int i=0;i<jbs.size();i++){
				jbs.get(i).setText(names.get(i));
				jbs.get(i).setEnabled(flags.get(i));
			}
		};
		
		jbs.forEach(x->{
			x.addActionListener(ac);
			this.getContentPane().add(x);
		});
				
		this.setVisible(true);
	}


}
