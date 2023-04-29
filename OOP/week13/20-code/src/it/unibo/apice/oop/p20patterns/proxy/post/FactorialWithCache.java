package it.unibo.apice.oop.p20patterns.proxy.post;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FactorialWithCache implements Factorial {
	
	private final Factorial base = new FactorialImpl();
	
	private final Map<Integer,Integer> map = new HashMap<>();
	
	public int factorial(int i) {
		return this.map.computeIfAbsent(i, base::factorial);
	}
}
