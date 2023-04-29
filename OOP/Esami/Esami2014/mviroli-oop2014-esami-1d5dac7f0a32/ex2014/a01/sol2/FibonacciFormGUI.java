package ex2014.a01.sol2;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.stream.*;

/**
 * Qualche nota su questa soluzione
 * - è solo una delle possibili
 * - usa alcune delle funzionalità di Java 8, come gli Optional e gli Stream, ma questo non era necessario
 * - può essere considerata come soluzione allo "stato dell'arte", e quindi approfondibile dallo studente per apprendere nuove tecniche  
 */

public class FibonacciFormGUI extends JFrame{

	private static final long serialVersionUID = -2660962647837757303L;
	
	private final Map<String,JTextField> map = new HashMap<>();
	private final FormStrategy strategy;
	
	public FibonacciFormGUI(int size){
		// Inizializzazione base
		this.strategy = new FibonacciFormStrategy(size); 
		final java.util.List<String> fieldNames = strategy.fieldNames();
		this.setSize(500, 40*fieldNames.size()+80);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		// Pannello sud
		final JPanel south = new JPanel(new FlowLayout());
		final JButton ok = new JButton("OK");
		south.add(ok);
		this.getContentPane().add(BorderLayout.SOUTH,south);
		
		// Pannello centrale
		final JPanel center = new JPanel(new GridLayout(0,2));
		strategy.fieldNames().forEach(s->{
			center.add(wrapperPanel(new JLabel(s),FlowLayout.RIGHT));
			map.put(s,new JTextField(20));
			center.add(wrapperPanel(map.get(s),FlowLayout.CENTER));
		});
		this.getContentPane().add(BorderLayout.CENTER,center);
		
		// Gestione handler ok
		
		ok.addActionListener(e->{
			final Map<String,String> mapToPass = map.entrySet()
													.stream()
													.collect(Collectors.toMap(x->x.getKey(),x->x.getValue().getText()));
			if (strategy.fieldsFilter(mapToPass)){
				strategy.onResult(mapToPass);
			}
			
		});
		this.setVisible(true);
							
	}
	
	private static JPanel wrapperPanel(final JComponent component, final int orientation){
		final JPanel panel = new JPanel(new FlowLayout(orientation));
		panel.add(component);
		return  panel;
	}
}
