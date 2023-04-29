package it.unibo.apice.oop.p20patterns.proxy.bad;

import java.util.HashMap;
import java.util.Map;

public class Factorial {

	private final static Map<Integer, Integer> factCache = new HashMap<>();

	public static int factorial(int i) {
		if (!factCache.containsKey(i)) {
			factCache.put(i, factorial2(i));
		}
		return factCache.get(i);
	}

	private static int factorial2(int i) {
		return i == 0 ? 1 : i * factorial2(i - 1);
	}
}
