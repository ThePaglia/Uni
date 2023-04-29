package a04.sol2;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Arrays;
import java.util.stream.*;

import javax.swing.*;


public class GUI extends JFrame{
	
	private final java.util.List<JButton> jbs;
	private final Logics cs = new LogicsImpl();
	
	public GUI(){
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		ActionListener ac = e -> {
			final JButton jb = (JButton)e.getSource();
			jb.setEnabled(false);
		};
		jbs = Stream.generate(()->new JButton())
				      .limit(5)
				      .peek(jb->jb.addActionListener(ac))
				      .peek(jb->getContentPane().add(jb))
				      .collect(Collectors.toList());
		this.fillButtons();
		final JButton draw = new JButton("Draw");
		this.getContentPane().add(draw);
		draw.addActionListener(x->{
			cs.redraw(jbs.stream().map(jb->!jb.isEnabled()).collect(Collectors.toList()));
			fillButtons();
			System.out.println(cs.getResult());
		});
		
		this.setVisible(true);
	}
	
	private void fillButtons() {
		List<Integer> l = cs.getDices();
		for (int i=0;i<5;i++) {
			jbs.get(i).setEnabled(true);
			jbs.get(i).setText(String.valueOf(l.get(i)));
		}
	}
	
	public static void main(String[] args) {
		new GUI();
	}

}

