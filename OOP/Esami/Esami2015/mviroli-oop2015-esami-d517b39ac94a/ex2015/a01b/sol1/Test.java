package ex2015.a01b.sol1;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.*;

import ex2015.a01b.sol1.CoursesManagement.Course;

/**
 * Implementare l'interfaccia CoursesManagement data tramite una classe
 * CoursesManagementImpl con costruttore senza argomenti. Modella la gestione
 * dell'assegnamento dei corsi di una università, con un metodo per assegnare un
 * corso di un certo anno ad un professore ed un metodo per aggiungere ad un
 * corso di un certo anno uno o più tutor (massimo 3).
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti Javadoc
 * dell'interfaccia CoursesManagement costituiscono la definizione del problema da
 * risolvere.
 * I test il cui nome comincia con 'optional' sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio -- anche se concorrono alla definizione del punteggio.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {

    private static final String CASADEI = "Matteo Casadei";
    private static final String CICOGNANI = "Massimo Cicognani";
    private static final String CROATTI = "Angelo Croatti";
    private static final String MONTAGNA = "Sara Montagna";
    private static final String MULAZZANI = "Michele Mulazzani";
    private static final String PICCININI = "Maurizio Piccinini";
    private static final String PIANINI = "Danilo Pianini";
    private static final String RICCI = "Alessandro Ricci";
    private static final String SANTI = "Andrea Santi";
    private static final String VIROLI = "Mirko Viroli";

    private static void populateAssignment(final CoursesManagement c) {
    	// crea un assegnamento di corsi a prof e tutor
        IntStream.rangeClosed(2012, 2015).forEach(year -> { // year compreso fra 2012 e 2015 inclusi..
            c.assignProfessor(Course.ANALYSIS, year, CICOGNANI);
            c.assignProfessor(Course.GEOMETRY, year, MULAZZANI);
            c.assignProfessor(Course.PHYSICS, year, PICCININI);
            c.assignTutor(Course.FOUNDATIONS_OF_INFORMATICS, year, MONTAGNA);
        });
        IntStream.rangeClosed(2012, 2013).forEach(year -> {
            c.assignProfessor(Course.OPERATING_SYSTEMS, year, RICCI);
            c.assignTutor(Course.OPERATING_SYSTEMS, year, SANTI);
        });
        IntStream.rangeClosed(2013, 2015).forEach(year -> {
            c.assignProfessor(Course.OBJECT_ORIENTED_PROGRAMMING, year, VIROLI);
            c.assignTutor(Course.OBJECT_ORIENTED_PROGRAMMING, year, PIANINI);
        });
        IntStream.rangeClosed(2012, 2014).forEach(year -> {
            c.assignProfessor(Course.FOUNDATIONS_OF_INFORMATICS, year, VIROLI);
        });
        c.assignTutor(Course.FOUNDATIONS_OF_INFORMATICS, 2012, PIANINI);
        c.assignTutor(Course.FOUNDATIONS_OF_INFORMATICS, 2013, PIANINI);
        c.assignTutor(Course.FOUNDATIONS_OF_INFORMATICS, 2014, PIANINI);
        c.assignTutor(Course.OBJECT_ORIENTED_PROGRAMMING, 2013, SANTI);
        c.assignTutor(Course.OBJECT_ORIENTED_PROGRAMMING, 2014, CASADEI);
        c.assignTutor(Course.OBJECT_ORIENTED_PROGRAMMING, 2015, CASADEI);
        c.assignTutor(Course.OBJECT_ORIENTED_PROGRAMMING, 2015, CROATTI);
    }
    
    private static CoursesManagement makeCalendar() {
        return new CoursesManagementImpl();
    }

    @org.junit.Test
    public void testUnassignedCourses() {
        final CoursesManagement cc = makeCalendar();
        // verifica che inizialmente tutti i corsi del 2015 NON sono assegnati
        assertThat(cc.getUnassignedCourses(2015), hasItems(Course.values()));
        // popola l'assegnamento
        populateAssignment(cc);
        // due corsi non assegnati nel 2015
        assertEquals(2, cc.getUnassignedCourses(2015).size());
        assertThat(cc.getUnassignedCourses(2015),
                hasItems(Course.FOUNDATIONS_OF_INFORMATICS, Course.OPERATING_SYSTEMS));
        // un corso non assegnato nel 2014, etc..
        assertEquals(1, cc.getUnassignedCourses(2014).size());
        assertThat(cc.getUnassignedCourses(2014), hasItems(Course.OPERATING_SYSTEMS));
        assertTrue(cc.getUnassignedCourses(2013).isEmpty());
        assertEquals(1, cc.getUnassignedCourses(2012).size());
        assertThat(cc.getUnassignedCourses(2012), hasItems(Course.OBJECT_ORIENTED_PROGRAMMING));
    }

    @org.junit.Test
    public void testExceptions() {
        final CoursesManagement cc = makeCalendar();
        populateAssignment(cc);
        try {
            cc.assignProfessor(Course.OBJECT_ORIENTED_PROGRAMMING, 2014, RICCI);
            fail("prof already assigned!");
        } catch (final IllegalStateException e) {
        } catch (Exception e){
        	fail("wrong exception thrown");
        }
        try {
            cc.assignTutor(Course.OBJECT_ORIENTED_PROGRAMMING, 2015, SANTI);
            fail("3 tutors already assigned");
        } catch (final IllegalStateException e) {
        } catch (Exception e){
        	fail("wrong exception thrown");
        }
    }

    @org.junit.Test
    public void testCoursesByProf() {
        final CoursesManagement cc = makeCalendar();
        assertTrue(cc.getCoursesByProf(VIROLI).isEmpty());
        populateAssignment(cc);
        assertEquals(6, cc.getCoursesByProf(VIROLI).size());
        assertEquals(2, cc.getCoursesByProf(RICCI).size());
        assertTrue(cc.getCoursesByProf(RICCI).contains(new Pair<>(2012,Course.OPERATING_SYSTEMS)));
        assertTrue(cc.getCoursesByProf(RICCI).contains(new Pair<>(2013,Course.OPERATING_SYSTEMS)));
        assertTrue(cc.getCoursesByProf(CASADEI).isEmpty());
        assertTrue(cc.getCoursesByProf(CROATTI).isEmpty());
        assertTrue(cc.getCoursesByProf(PIANINI).isEmpty());
        assertTrue(cc.getCoursesByProf(SANTI).isEmpty());
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void optionalTestRemainingTutoringSlots() {
        final CoursesManagement cc = makeCalendar();
        populateAssignment(cc);
        // vi sono slot per tutor ogni anno, sia il 2012, 2013, 2014, 2015
        assertEquals(cc.getRemainingTutoringSlots().size(),4);
        // slot rimanenti per il 2012
        Set<Pair<Course, Integer>> set = cc.getRemainingTutoringSlots().get(2012);
        assertTrue(set.contains(new Pair<>(Course.OPERATING_SYSTEMS,2)));
        assertTrue(set.contains(new Pair<>(Course.PHYSICS,3)));
        assertTrue(set.contains(new Pair<>(Course.FOUNDATIONS_OF_INFORMATICS,1)));
        assertTrue(set.contains(new Pair<>(Course.OBJECT_ORIENTED_PROGRAMMING,3)));
        assertTrue(set.contains(new Pair<>(Course.ANALYSIS,3)));
        // slot rimanenti per il 2015
        final Set<Pair<Course, Integer>> set2 = cc.getRemainingTutoringSlots().get(2015);
        assertThat(set2, hasItems(
                new Pair<>(Course.OBJECT_ORIENTED_PROGRAMMING, 0),
                new Pair<>(Course.FOUNDATIONS_OF_INFORMATICS, 2),
                new Pair<>(Course.OPERATING_SYSTEMS, 3)));
    }
    

}
