package it.unibo.apice.oop.p20patterns.factory;

import javax.swing.*;

public interface GUIFactory {
	// 3 factory methods
	JPanel createPanel(String text);

	JComponent createButton(String text);
	
	JComponent createLabel(String text);
}
