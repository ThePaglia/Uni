package ex2015.a03a.e1;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Implementare l'interfaccia Faculty data tramite una classe FacultyImpl
 * con costruttore senza argomenti. Modella la gestione dei corsi di una università, 
 * con metodi per registrare corsi e studenti, impostando quale studente segue quali corsi.
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti 
 * dell'interfaccia Faculty costituisce la definizione del problema da
 * risolvere.
 * 
 * I test il cui nome comincia con 'optional', e le richieste di performance indicate
 * nell'interfaccia Faculty, sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio -- anche se concorrono alla definizione del punteggio.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {

    private static void populateFaculty(final Faculty f) {
        f.registerCourse(100, "OOP"); // 3 courses
        f.registerCourse(101, "SISOP");
        f.registerCourse(102, "ENG");
        
        f.registerStudent(1000, "Rossi"); // 5 students
        f.registerStudent(1001, "Bianchi");
        f.registerStudent(1002, "Verdi");
        f.registerStudent(1003, "Neri");
        f.registerStudent(1004, "Rosa");
        
        f.associate(1000, 100); // all students do OOP
        f.associate(1001, 100);
        f.associate(1002, 100);
        f.associate(1003, 100);
        f.associate(1004, 100);
        
        f.associate(1000, 101); // three students do SISOP
        f.associate(1001, 101);
        f.associate(1002, 101);
        
        f.associate(1000, 102); // only one student does ENG
    }
    
    /*
    @org.junit.Test
    public void testBasicSelectors() {
        Faculty f = new FacultyImpl();
        populateFaculty(f);
        // accesso a corsi e studenti per id
        assertEquals(f.course(100),"OOP");
        assertEquals(f.course(101),"SISOP");
        assertEquals(f.student(1000),"Rossi");
        assertEquals(f.student(1001),"Bianchi");
        
        // accesso ai corsi di uno studente
        assertThat(f.coursesByStudent(1000), hasItems(100,101,102)); 
        assertEquals(f.coursesByStudent(1000).size(),3);
        assertThat(f.coursesByStudent(1001), hasItems(100,101)); 
        assertEquals(f.coursesByStudent(1001).size(),2);
        assertThat(f.coursesByStudent(1003), hasItems(100)); 
        assertEquals(f.coursesByStudent(1003).size(),1);
        
        // accesso agli studenti di un corso
        assertThat(f.studentsByCourse(100), hasItems(1000,1001,1002,1003,1004));
        assertEquals(f.studentsByCourse(100).size(),5);
        assertThat(f.studentsByCourse(101), hasItems(1000,1001,1002));
        assertEquals(f.studentsByCourse(101).size(),3);
        assertThat(f.studentsByCourse(102), hasItems(1000));
        assertEquals(f.studentsByCourse(102).size(),1);
        
        // mappa corsi -> set di studenti
        assertThat(f.mapCoursesStudents().get("OOP"), hasItems("Rossi","Bianchi","Verdi","Neri","Rosa"));
        assertEquals(f.mapCoursesStudents().get("OOP").size(),5);
        assertThat(f.mapCoursesStudents().get("SISOP"), hasItems("Rossi","Bianchi","Verdi"));
        assertEquals(f.mapCoursesStudents().get("SISOP").size(),3);
    }
    
    @org.junit.Test
    public void optionalTestExceptions() {
        Faculty f = new FacultyImpl();
        f.registerCourse(1, "OOP");
        try{
            f.registerCourse(1, "SISOP");
            fail("can't register a course twice");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        f.registerStudent(10, "Mirko");
        try{
            f.registerStudent(10, "Gino");
            fail("can't register a student twice");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        f.associate(10, 1);
        try{
            f.associate(10,1);
            fail("already associated");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        try{
            f.associate(10,2);
            fail("can't associate non-existing elements");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
    }
    */
    
   

}
