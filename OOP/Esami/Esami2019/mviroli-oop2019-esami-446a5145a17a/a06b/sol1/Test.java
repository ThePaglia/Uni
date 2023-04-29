package a06b.sol1;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test {
	
	/*
	 * Implementare l'interfaccia MiniAssemblyMachine come indicato nel metodo initFactory qui sotto.
	 * Realizza un interprete per un mini-assembly con sole istruzioni INC (incrementa un registro), 
	 * DEC (decrementa un registro), JNZ (Jump se un registro non è zero) e RET (termina l'esecuzione).
	 * 
	 * Il commento alle interfacce fornite, e i metodi di test qui sotto costituiscono la necessaria 
	 * spiegazione del problema.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, 
	 * ma concorrono comunque al raggiungimento della totalità del punteggio:
	 * - implementazione dei test chiamati optionalTestXYZ (relativi ai check delle eccezioni) 
	 * - la buona progettazione della soluzione, in particolare con minimizzazione di ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio:
	 * - correttezza della parte obbligatoria: 9 punti
	 * - correttezza della parte opzionale: 4 punti
	 * - qualità della soluzione: 4 punti
	 * 
	 */
	
	private MiniAssemblyMachine machine = null;
	
	@org.junit.Before
	public void initFactory() {
		this.machine = new MiniAssemblyMachineImpl();
	}
	
	@org.junit.Test
	public void testInc() {
		this.machine.inc(0); // 0: INC R0
		this.machine.inc(1); // 1: INC R1
		this.machine.inc(1); // 2: INC R1
		this.machine.ret();  // 3: RET
		// Input: R0 = 10, R1 = 20, R2 = 30 
		// Output: R0 = 11, R1 = 22, R2 = 30
		assertEquals(List.of(11,22,30),this.machine.execute(List.of(10,20,30)));
	}
	
	@org.junit.Test
	public void testDec() {
		this.machine.dec(0); // 0: DEC R0
		this.machine.dec(1); // 1: DEC R1
		this.machine.dec(1); // 2: DEC R1
		this.machine.ret();  // 3: RET
		assertEquals(List.of(9,18,30),this.machine.execute(List.of(10,20,30)));
	}

	
	@org.junit.Test
	public void testJnz() {
		this.machine.jnz(0, 2); // 0: JNZ R0, #2
		this.machine.ret();		// 1: RET
		this.machine.inc(0);	// 2: INC R0
		this.machine.ret();		// 3: RET
		assertEquals(List.of(11,20,30),this.machine.execute(List.of(10,20,30)));
		assertEquals(List.of(0,20,30),this.machine.execute(List.of(0,20,30)));
	}
	
	@org.junit.Test
	public void testSum() {
		this.machine.jnz(0, 2);	// 0: JNZ R0, #2
		this.machine.ret();		// 1: RET
		this.machine.dec(0);	// 2: DEC R0
		this.machine.inc(1);	// 3: INC R1
		this.machine.jnz(0, 2);	// 4: JNZ R0, #2
		this.machine.ret();		// 5: RET
		assertEquals(List.of(0,300),this.machine.execute(List.of(100,200)));
	}
	
	@org.junit.Test
	public void optionalTestBadJump() {
		// La prossima istruzione, per JUMP o normale esecuzione sequenziale, ci deve essere!
		this.machine.jnz(0, 2);
		this.machine.ret();
		try {
			this.machine.execute(List.of(1));
			fail("Eccezione 'bad jump' necessaria");
		} catch (IllegalStateException e){
			
		} catch (Exception e) {
			fail("Eccezione erronea");
		}
	}
	
	@org.junit.Test
	public void optionalTestBadRegister() {
		// Attenzione a non accedere a registri non esistenti!
		this.machine.inc(1);
		this.machine.ret();
		try {
			this.machine.execute(List.of(1));
			fail("Eccezione 'wrong register' necessaria");
		} catch (IndexOutOfBoundsException e){
			
		} catch (Exception e) {
			fail("Eccezione erronea");
		}
	}
	

	
}

