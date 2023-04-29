package it.unibo.apice.oop.p20patterns.proxy.pre;

public class Factorial {
	
	// Problem: add caching for input -> output, checking input
	 
	public static int factorial(int i) {
		return i==0 ? 1 : i*factorial(i-1); 
	}
}
