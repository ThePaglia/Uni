package it.unibo.apice.oop.p20patterns.proxy.bad;

public class UseFactorial {
	
	public static void main(String[] args) {
		int[] vals = new int[] {5,3,7,5,2,3,4,1,0};
		for (int val: vals) {
			int fact = Factorial.factorial(val);
			System.out.println("Fact: " + val + "\t->\t" + fact);
		}
	}

}
