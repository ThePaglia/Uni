package a02a.sol1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractDiet implements Diet {
	
	private final Map<String,Map<Nutrient, Integer>> products = new HashMap<>();

	@Override
	public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
		if (this.products.containsKey(name)) {
			throw new IllegalArgumentException();
		}
		this.products.put(name, nutritionMap);

	}

	@Override
	public boolean isValid(Map<String, Double> dietMap) {
		Map<Nutrient,Double> calories = new HashMap<>();
		System.out.println(dietMap);	
		for (var e: dietMap.entrySet()) {
			for (var e2: this.products.get(e.getKey()).entrySet()) {
				calories.merge(e2.getKey(), e.getValue()/100.0*e2.getValue(), (a,b) -> a+b);
			}
		}
		System.out.println(calories);
		return this.getConstraints().stream().allMatch(c -> isConstraintValid(c,calories));
	}
	
	private boolean isConstraintValid(Pair<Predicate<Nutrient>, Predicate<Double>> c, Map<Nutrient, Double> calories) {
		return c.get2().test(calories.entrySet()
				.stream()
				.filter(e -> c.get1().test(e.getKey()))
				.mapToDouble(Map.Entry::getValue)
				.sum());
	}

	protected abstract Set<Pair<Predicate<Nutrient>,Predicate<Double>>> getConstraints();

}
