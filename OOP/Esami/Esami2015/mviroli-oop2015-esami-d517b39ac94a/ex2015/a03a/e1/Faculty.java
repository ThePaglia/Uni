package ex2015.a03a.e1;

import java.util.Map;
import java.util.Set;

/* Requirements (opzionali per considerare la soluzione corretta):
 * - tutti i metodi tranne l'ultimo da eseguire in tempo costante (ammortizzato), O(1)
 * - si assuma che il metodo java.util.Collections.unmodifiableSet è eseguito in tempo costante
 * - si effettuino le copie difensive del caso per evitare di violare l'incapsulamento
 */
public interface Faculty {
    
    // registra un nuovo corso, dati id univoco e nome
    void registerCourse(int id, String name);
    
    // registra un nuovo studente, dati id univoco e nome
    void registerStudent(int id, String name);
    
    // associa studente a corso
    void associate(int studentId, int courseId);
    
    // ritorna il nome di un corso a partire dal suo id
    String course(int courseId);
    
    // ritorna il nome di uno studente a partire dal suo id
    String student(int studentId);
    
    // ritorna il set degli id dei corsi seguiti da uno studente 
    Set<Integer> coursesByStudent(int studentId);
    
    // ritorna il set degli id degli studenti che seguono un corso  
    Set<Integer> studentsByCourse(int courseId);
    
    // torna una mappa che associa ad ogni nome di corso il set dei nomi dei suoi studenti
    Map<String,Set<String>> mapCoursesStudents();
    
}
