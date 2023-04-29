package ex2015.a03b.e1;

import java.util.Map;

/* Requirements (opzionali per considerare la soluzione corretta):
 * - tutti i metodi tranne gli ultimi due da eseguire in tempo costante (ammortizzato), O(1)
 * - si effettuino le copie difensive del caso per evitare di violare l'incapsulamento
 */
public interface ThesesManagement {
    
    enum Status {
        TO_BE_APPROVED, APPROVED, SUBMITTED, CONCLUDED 
    }
    
    // registra un nuovo studente, dati id univoco e nome
    void registerStudent(int studentId, String name);
    
    // registra una nuovo tesi, dati id univoco, titolo e id studente
    void registerThesis(int thesisId, String title, int studentId);
    
    // marca la tesi come approvata
    void thesisApproved(int thesisId);
    
    // marca la tesi come sottoposta, con nuovo titolo
    void thesisSubmitted(int thesisId, String finalTitle);
    
    // marca la tesi come conclusa, con un voto in 110-esimi
    void thesisConcluded(int thesisId, int score);
    
    // data una tesi, ritorna una coppia titolo/stato
    Pair<String,Status> thesis(int thesisId);
    
    // torna una mappa che associa nome studente a stato della tesi
    Map<String, Status> statusByStudent();
    
    // torna la media dei voti per le tesi concluse
    double averageThesisScore();
    
}
