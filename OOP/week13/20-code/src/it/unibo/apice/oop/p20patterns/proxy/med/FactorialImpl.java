package it.unibo.apice.oop.p20patterns.proxy.med;

public class FactorialImpl implements Factorial {

	public int factorial(int i) {
		return i == 0 ? 1 : i * factorial(i - 1);
	}
}
