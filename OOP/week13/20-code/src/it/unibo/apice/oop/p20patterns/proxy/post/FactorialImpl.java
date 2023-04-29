package it.unibo.apice.oop.p20patterns.proxy.post;

public class FactorialImpl implements Factorial {

	@Override
	public int factorial(int i) {
		return i == 0 ? 1 : i * factorial(i - 1);
	}
}
