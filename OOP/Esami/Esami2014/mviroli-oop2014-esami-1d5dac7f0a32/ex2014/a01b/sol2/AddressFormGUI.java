package ex2014.a01b.sol2;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.stream.*;

/**
 * Qualche nota su questa soluzione
 * - è solo una delle possibili
 * - usa alcune delle funzionalità di Java 8, come gli Optional e gli Stream, ma questo non era necessario
 * - può essere considerata come soluzione allo "stato dell'arte", e quindi approfondibile dallo studente per apprendere nuove tecniche  
 */

public class AddressFormGUI extends JFrame{

	private static final long serialVersionUID = -2660962647837757303L;
	
	private final Map<FormField,JTextField> map = new HashMap<>();
	private final FormStrategy<String> strategy = new AddressFormStrategy();
	
	public AddressFormGUI(){
		// Inizializzazione base
		final java.util.List<FormField> fields = strategy.fields();
		this.setSize(700, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		// Pannello sud
		final JPanel south = new JPanel(new FlowLayout());
		final JButton ok = new JButton("OK");
		south.add(ok);
		this.getContentPane().add(BorderLayout.SOUTH,south);
		
		// Pannello centrale
		final JPanel center = new JPanel(new GridLayout(0,2));
		fields.forEach(f->{
			center.add(wrapperPanel(new JLabel(f.getNome()),FlowLayout.RIGHT));
			map.put(f,new JTextField(f.getLength()));
			center.add(wrapperPanel(map.get(f),FlowLayout.LEFT));
		});
		this.getContentPane().add(BorderLayout.CENTER,center);
		
		// Gestione handler ok
		
		ok.addActionListener(e->{
			final Map<FormField,String> mapToPass = fields.stream()
														  .collect(Collectors.toMap(f->f,f->map.get(f).getText()));
			
			if (mapToPass.entrySet().stream().allMatch(x->x.getKey().getPred().test(x.getValue()))){
				System.out.println(strategy.result(fields.stream().map(f->map.get(f).getText()).collect(Collectors.toList())));
			} else {
				System.out.println("Errore");
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
