package it.unibo.apice.oop.p11gencoll2.wildcard;
public class Contra{
    
    public static void addStrings(Vector<? super String> vect, String s, int n){
    	// Questo metodo usa solo 'void addElement(X x)'
    	// Quindi l'input può essere contra-variante!
    	for (int i=0;i<n;i++){
    	    vect.addElement(s);
    	}
    }
    
    public static void main(String[] s){
    	Vector<Object> vector = new Vector<>();
    	vector.addElement(1);
    	vector.addElement(new java.util.Date());
    	// Posso passare Vector<Object> dove si attende
    	// Vector<? super String>
    	addStrings(vector,"aggiunta",10);
    	System.out.println(vector);
    }
} 
