package it.unibo.apice.oop.p20patterns.proxy.med;

public class UseFactorial {
	
	public static void main(String[] args) {
		Factorial algorithms = new FactorialImpl();
		int[] vals = new int[] {5,3,7,5,2,3,4,1,0};
		for (int val: vals) {
			int fact = algorithms.factorial(val);
			System.out.println("Fact: " + val + "\t->\t" + fact);
		}
	}

}
