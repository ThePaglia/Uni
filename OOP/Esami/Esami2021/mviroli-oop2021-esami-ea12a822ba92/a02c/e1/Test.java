package a02c.e1;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import a02c.e1.UniversityProgram.Group;

import static a02c.e1.UniversityProgram.Group.*;

public class Test {

	/*
	 * Implementare l'interfaccia UniversityProgramFactory come indicato nel metodo
	 * initFactory qui sotto. Realizza una factory per un concetto di corso di laurea (program), 
	 * catturato dall'interfaccia UniversityProgram.
	 * 
	 * Come si può vedere nel metodo fillProgram qui sotto, ad un CDL si
	 * associa innanzitutto un insieme di corsi (chiave della mappa), ognuno con un certo numero di 
	 * crediti e un insieme di gruppi di corsi (come coppia, valore della mappa), 
	 * ad esempio: 
	 * map.put("AI", new Pair<>(Set.of(OPTIONAL, OPTIONAL_A), 6));
	 * significa che il corso AI ha 6 CFU ed è usabile sia nel gruppo OPTIONAL che in OPTIONAL_A
	 * 
	 * A quel punto il CDL ha un metodo per stabilire se una certa selezione di corsi (ossia
	 * un curriculum presentato da uno studente) soddisfa o meno i requisiti, espressi da una logica
	 * CDL-specifica, in termini di numero di crediti (minimi e/o massimi) per ogni gruppo. Questa
	 * logica è specificata nella factory. Se ad esempio si dice che gli OPTIONAL devono essere al 
	 * massimo 12 CFU significa che la somma dei CFU dei corsi che posso appartenere al gruppo
	 * OPTIONAL deve essere al massimo 12 CFU.
	 * 
	 * Sono considerati opzionali ai fini della possibilità di correggere
	 * l'esercizio, ma concorrono comunque al raggiungimento della totalità del
	 * punteggio: 
	 * 
	 * - implementazione dei quattro metodi della factory (ossia, nella parte obbligatoria è sufficiente 
	 * implementarne 3 a piacimento) 
	 * - la buona progettazione della soluzione, utilizzando patterns che portino a codice succinto 
	 * che evita ripetizioni
	 * 
	 * Si tolga il commento dal metodo initFactory.
	 * 
	 * Indicazioni di punteggio: 
	 * - correttezza della parte obbligatoria: 10 punti 
	 * - correttezza della parte opzionale: 3 punti (ulteriore metodo della factory) 
	 * - qualità della soluzione: 4 punti (per buon design)
	 * 
	 */

	private UniversityProgramFactory factory = null;
	
	@org.junit.Before
	public void initFactory() {
		//this.factory = new UniversityProgramFactoryImpl();
	}
	
	private void fillProgram(UniversityProgram program) {
		Map<String,Pair<Set<Group>,Integer>> map = new HashMap<>();
		map.put("PROG_LANGS", new Pair<>(Set.of(MANDATORY), 12));
		map.put("DIST_SYS", new Pair<>(Set.of(MANDATORY), 6));
		map.put("SOFT_ENG", new Pair<>(Set.of(MANDATORY), 12));
		map.put("TECH_WEB", new Pair<>(Set.of(MANDATORY), 6));
		map.put("AI", new Pair<>(Set.of(OPTIONAL, OPTIONAL_A), 6));
		map.put("SMART_CITY", new Pair<>(Set.of(OPTIONAL, OPTIONAL_A), 6));
		map.put("ROBOTICS", new Pair<>(Set.of(OPTIONAL, OPTIONAL_A), 6));
		map.put("DATA_MINING", new Pair<>(Set.of(OPTIONAL, OPTIONAL_B), 12));
		map.put("BUS_ANALYTICS", new Pair<>(Set.of(OPTIONAL, OPTIONAL_B), 6));
		map.put("SEM_WEB", new Pair<>(Set.of(OPTIONAL, OPTIONAL_B), 6));
		map.put("DEEP_LEARN", new Pair<>(Set.of(OPTIONAL, OPTIONAL_A, OPTIONAL_B), 12));
		map.put("NETWORKS", new Pair<>(Set.of(FREE), 6));
		map.put("VISION", new Pair<>(Set.of(FREE), 6));
		map.put("THESIS", new Pair<>(Set.of(THESIS), 12));
		program.setCourses(map);
	}
	
	@org.junit.Test
	public void testFlexible() {
		var program = this.factory.flexible();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PROG_LANGS", "AI", "DEEP_LEARN", "NETWORKS", "THESIS"))); // 48 CFU
		assertFalse(program.isValid(Set.of("PROG_LANGS", "AI", "DEEP_LEARN", "NETWORKS"))); // not 48 CFU
		assertFalse(program.isValid(Set.of("PROG_LANGS", "AI", "DEEP_LEARN", "NETWORKS", "THESIS","SEM_WEB"))); // not 48 CFU
	}
	
	@org.junit.Test
	public void testFixed() {
		var program = this.factory.fixed();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PROG_LANGS", "AI", "ROBOTICS", "DEEP_LEARN", "DATA_MINING", "THESIS"))); // OK
		assertFalse(program.isValid(Set.of("PROG_LANGS", "TECH_WEB", "ROBOTICS", "DEEP_LEARN", "DATA_MINING", "THESIS"))); // Too much "mandatory"
		assertFalse(program.isValid(Set.of("PROG_LANGS", "VISION", "ROBOTICS", "DEEP_LEARN", "DATA_MINING", "THESIS"))); // No Free course allowed
	}
	
	@org.junit.Test
	public void testBalanced() {
		var program = this.factory.balanced();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PROG_LANGS", "SOFT_ENG", "DEEP_LEARN", "DATA_MINING", "THESIS"))); // ok
		assertFalse(program.isValid(Set.of("PROG_LANGS", "SOFT_ENG", "DEEP_LEARN", "ROBOTICS", "VISION", "THESIS"))); // Thesis+Free > 12
		assertFalse(program.isValid(Set.of("PROG_LANGS", "AI", "ROBOTICS", "DEEP_LEARN", "DATA_MINING", "THESIS"))); // not enough mandatory
	}
	
	@org.junit.Test
	public void testStructured() {
		var program = this.factory.structured();
		fillProgram(program);
		assertTrue(program.isValid(Set.of("PROG_LANGS", "SMART_CITY", "DATA_MINING", "DEEP_LEARN", "NETWORKS", "THESIS"))); // ok
		assertFalse(program.isValid(Set.of("PROG_LANGS", "SEM_WEB", "DATA_MINING", "BUS_ANALYTICS", "NETWORKS", "THESIS"))); // not ok
	}
}
