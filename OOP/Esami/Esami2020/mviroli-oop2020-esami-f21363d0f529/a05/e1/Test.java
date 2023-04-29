package a05.e1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Assertions;

public class Test {

	/*
	 * Implementare l'interfaccia batteryFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per dei Battery, ossia
	 * pile che mano a mano che vengono usate si scaricano.
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto
	 * costituiscono la necessaria spiegazione del problema.
	 * 
	 * Sono considerati invece opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione degli ultimi due test, chiamati optionalTestXYZ (relativi ai
	 * metodo BatteryFactory.createSecureAndRechargableBattery e BatteryFactory.createSimpleBatteryByDrop) 
	 * 
	 * - la buona progettazione della soluzione, in particolare utilizzando codice succinto
	 * 
	 * - ATTENZIONE: E' considerato obbligatorio evitare le principali ripetizioni, pena quindi
	 * decurtazione di qualche punto anche nella parte obbligatoria 
	 * 
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * 
	 * - correttezza della parte obbligatoria: 10 punti 
	 * 
	 * - correttezza della parte opzionale: 4 punti 
	 * 
	 * - qualità della soluzione: 3 punti
	 * 
	 */

	private BatteryFactory factory = null;

	@org.junit.Before
	public void initFactory() {
		//this.factory = new BatteryFactoryImpl();
	}
	
	private static final double PRECISION = 0.01; // required when asserting equality of booleans

	
	/**
	 * This methods checks that battery reaches energy level1 after drop1 use, and level2 after further drop2 use
	 */
	private void basicChecks(Battery battery, double drop1, double level1, double drop2, double level2) {
		assertEquals(1.0, battery.getEnergy(), PRECISION);
		battery.startUse();
		battery.stopUse(drop1);
		assertEquals(level1, battery.getEnergy(), PRECISION);
		battery.startUse();
		battery.stopUse(drop2);
		assertEquals(level2, battery.getEnergy(), PRECISION);
	}
	
	@org.junit.Test
	public void testSimple() {
		var battery = this.factory.createSimpleBattery();
		// used 0.2, remains at 0.8; used 0.3, remains at 0.5
		basicChecks(battery, 0.2, 0.8, 0.3, 0.5); 
		battery.recharge(); // recharge has no effect
		assertEquals(0.5, battery.getEnergy(), PRECISION);
		battery.startUse(); // no effect, no exception
		battery.startUse(); // no effect, no exception
		battery.stopUse(0.1); // drops 0.1, no exception
		battery.stopUse(0.1); // drops 0.1, no exception
		assertEquals(0.3, battery.getEnergy(), PRECISION);
		battery.stopUse(0.5); // reaches 0 without problems
		assertEquals(0.0, battery.getEnergy(), PRECISION);
	}
	
	@org.junit.Test
	public void testExponential() {
		var battery = this.factory.createSimpleBatteryWithExponentialDrop();
		// used, remains at 0.5; used, remains at 0.25
		basicChecks(battery, 1.0, 0.5, 1.0, 0.25);
		battery.recharge(); // recharge has no effect
		assertEquals(0.25, battery.getEnergy(), PRECISION);
		// no exception are raised as for SimpleBattery above
	}
	
	@org.junit.Test
	public void testSecure() {
		var battery = this.factory.createSecureBattery();
		// As with simpleBattery
		basicChecks(battery, 0.2, 0.8, 0.3, 0.5);
		battery.recharge(); // recharge has no effect
		assertEquals(0.5, battery.getEnergy(), PRECISION);
		// stopping with starting gives exception 
		Assertions.assertThrows(IllegalStateException.class, ()->battery.stopUse(0.2));
		battery.startUse();
		// starting if already started gives exception
		Assertions.assertThrows(IllegalStateException.class, ()->battery.startUse());
		battery.stopUse(0.6);
		// starting with energy 0.0 gives exception
		Assertions.assertThrows(IllegalStateException.class, ()->battery.startUse());
	}
	
	@org.junit.Test
	public void testRecharge() {
		var battery = this.factory.createRechargeableBattery();
		// As with simpleBattery
		basicChecks(battery, 0.2, 0.8, 0.3, 0.5);
		battery.recharge(); // to 1
		assertEquals(1.0, battery.getEnergy(), PRECISION);
		battery.startUse();
		battery.stopUse(0.5);
		battery.recharge(); // to 0.99
		assertEquals(0.99, battery.getEnergy(), PRECISION);
		battery.startUse();
		battery.stopUse(0.5);
		battery.recharge(); // to 0.98
		assertEquals(0.98, battery.getEnergy(), PRECISION);
		
	}
	
	@org.junit.Test
	public void optionalTestByDrop() {
		// given the 2.0 rate, it means that with duration 0.1 it drops 0.2 of energy
		var battery = this.factory.createSimpleBatteryByDrop(2.0);
		// used 0.1, remains at 0.8; used 0.15, remains at 0.5
		basicChecks(battery, 0.1, 0.8, 0.15, 0.5);
		battery.recharge(); // recharge has no effect
		assertEquals(0.5, battery.getEnergy(), PRECISION);
		battery.startUse(); // no effect, no exception
		battery.startUse(); // no effect, no exception
		battery.stopUse(0.05); // drops 0.1, no exception
		battery.stopUse(0.05); // drops 0.1, no exception
		assertEquals(0.3, battery.getEnergy(), PRECISION);
		battery.stopUse(0.25); // reaches 0 without problems
		assertEquals(0.0, battery.getEnergy(), PRECISION);
	}
	
	@org.junit.Test
	public void optionalTestSecureAndRechargableBattery() {
		// Altogther simple, rechargable and secure
		var battery = this.factory.createSecureAndRechargeableBattery();
		basicChecks(battery, 0.2, 0.8, 0.3, 0.5);
		battery.recharge();
		assertEquals(1.0, battery.getEnergy(), PRECISION);
		battery.startUse();
		battery.stopUse(0.5);
		battery.recharge();
		assertEquals(0.99, battery.getEnergy(), PRECISION);
		battery.startUse();
		battery.stopUse(0.5);
		battery.recharge();
		assertEquals(0.98, battery.getEnergy(), PRECISION);
		Assertions.assertThrows(IllegalStateException.class, ()->battery.stopUse(0.2));
		battery.startUse();
		Assertions.assertThrows(IllegalStateException.class, ()->battery.startUse());
		battery.stopUse(1.0);
		Assertions.assertThrows(IllegalStateException.class, ()->battery.startUse());
	}
	
	
}
