package a05.sol2;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GUI extends JFrame{
	
	private final Logics cs;
	private final JButton play = new JButton("Play");
	private final List<JButton> aButtons;
	private final List<JButton> bButtons;
	
	
	public GUI(int size){
		this.cs = new LogicsImpl(size,size);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		JPanel jNorth = new JPanel();
		JPanel jSouth = new JPanel();
		JPanel jCenter = new JPanel();
		this.getContentPane().add(BorderLayout.NORTH, jNorth);
		this.getContentPane().add(BorderLayout.SOUTH, jSouth);
		this.getContentPane().add(BorderLayout.CENTER, jCenter);
		aButtons = Stream.generate(()->new JButton("*"))
				         .peek(jb -> jb.setEnabled(false))
				         .peek(jNorth::add)
				         .limit(cs.getPoints(Logics.Player.A))
				         .collect(Collectors.toList());
		bButtons = Stream.generate(()->new JButton("*"))
		         .peek(jb -> jb.setEnabled(false))
		         .peek(jSouth::add)
		         .limit(cs.getPoints(Logics.Player.B))
		         .collect(Collectors.toList());
		play.addActionListener(e -> {
			EnumMap<Logics.Player,Integer> draw = cs.draw();
			updatePoints();
			System.out.println(draw);
			if (cs.winner().isPresent()) {
				this.play.setText("WIN "+cs.winner().get());
				this.play.setEnabled(false);
			}
		});
		jCenter.add(play);
		updatePoints();
		this.pack();
		this.setVisible(true);
	}
	
	private void updatePoints() {
		for (int i=cs.getPoints(Logics.Player.A);i<aButtons.size();i++) {
			aButtons.get(i).setText(" ");
		}
		for (int i=cs.getPoints(Logics.Player.B);i<bButtons.size();i++) {
			bButtons.get(i).setText(" ");
		}
	}
	public static void main(String[] args) {
		new GUI(6);
	}

}

