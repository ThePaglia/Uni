package ex2015.a03b.sol1;

import static org.junit.Assert.*;

/**
 * Implementare l'interfaccia ThesesManagement (Gestione Tesi) data tramite una classe 
 * TheseManagementImpl con costruttore senza argomenti. Modella la gestione delle tesi
 * di un corso universitario, con metodi per registrare studenti e loro tesi, 
 * gestendo il ciclo di vita (da approvare/approvata/sottoposta/conclusa) della tesi.
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti 
 * dell'interfaccia ThesesManagement costituisce la definizione del problema da
 * risolvere.
 * 
 * I test il cui nome comincia con 'optional', e le richieste di performance indicate
 * nell'interfaccia ThesesManagement, sono considerati opzionali ai fini della possibilità di correggere
 * l'esercizio -- anche se concorrono alla definizione del punteggio.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {

    private static void populate(final ThesesManagement t) {
        t.registerStudent(1000, "Rossi"); // 5 students
        t.registerStudent(1001, "Bianchi");
        t.registerStudent(1002, "Verdi");
        t.registerStudent(1003, "Neri");
        t.registerStudent(1004, "Rosa");

        // only 4 students have a thesis
        t.registerThesis(1, "work on OOP", 1000);
        t.registerThesis(2, "work on SISOP", 1001);
        t.registerThesis(3, "work on ASD", 1002);
        t.registerThesis(4, "work on INGSOFT", 1003);

        // only 3 students have an approved thesis
        t.thesisApproved(1);
        t.thesisApproved(2);
        t.thesisApproved(3);

        // only 2 students have submitted a final version of the thesis
        t.thesisSubmitted(1, "work on OOP");
        t.thesisSubmitted(2, "survey on SISOP");

        // final vote
        t.thesisConcluded(1, 110);
        t.thesisConcluded(2, 109);
    }

    @org.junit.Test
    public void testBasicManagement() {
        ThesesManagement t = new ThesesManagementImpl();
        populate(t);
        // accesso alle tesi per id
        assertEquals(t.thesis(1), new Pair<>("work on OOP", ThesesManagement.Status.CONCLUDED));
        assertEquals(t.thesis(2), new Pair<>("survey on SISOP", ThesesManagement.Status.CONCLUDED));
        assertEquals(t.thesis(3), new Pair<>("work on ASD", ThesesManagement.Status.APPROVED));
        assertEquals(t.thesis(4), new Pair<>("work on INGSOFT", ThesesManagement.Status.TO_BE_APPROVED));
        
        // accesso alla mappa da studente a stato della tesi
        assertEquals(t.statusByStudent().get("Rossi"), ThesesManagement.Status.CONCLUDED);
        assertEquals(t.statusByStudent().get("Bianchi"), ThesesManagement.Status.CONCLUDED);
        assertEquals(t.statusByStudent().get("Verdi"), ThesesManagement.Status.APPROVED);
        assertEquals(t.statusByStudent().get("Neri"), ThesesManagement.Status.TO_BE_APPROVED);
        assertEquals(t.statusByStudent().size(), 4);

        // media dei voti delle tesi concluse, massimo errore 0.001
        assertEquals(t.averageThesisScore(), 109.5, 0.001);
    }

    @org.junit.Test
    public void optionalTestExceptions() {
        ThesesManagement t = new ThesesManagementImpl();
        t.registerStudent(1000, "Mirko");
        t.registerStudent(1001, "Mario");
        try {
            t.registerStudent(1000, "Gino");
            fail("can't reuse student id");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
        t.registerThesis(1, "work on OOP", 1000);
        try {
            t.registerThesis(2, "work on SISOP", 1000);
            fail("can't assign two thesis to the same student");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
        try {
            t.registerThesis(1, "work on SISOP", 1001);
            fail("can't reuse a thesis id");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
        t.thesisApproved(1);
        try {
            t.thesisApproved(1);
            fail("not to be approved");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
        t.thesisSubmitted(1, "final work on OOP");
        try {
            t.thesisSubmitted(1, "final work on OOP");
            fail("not to be submitted");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
        t.thesisConcluded(1, 110);
        try {
            t.thesisConcluded(1, 110);
            fail("not to be concluded");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
    }
}
