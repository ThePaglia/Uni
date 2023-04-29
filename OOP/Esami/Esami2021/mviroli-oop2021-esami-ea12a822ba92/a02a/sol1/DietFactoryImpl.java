package a02a.sol1;

import static a02a.sol1.Diet.Nutrient.*;

import java.util.Set;
import java.util.function.Predicate;

public class DietFactoryImpl implements DietFactory {

	@Override
	public Diet standard() {
		return new AbstractDiet() {

			@Override
			protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> getConstraints() {
				return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
						new Pair<>(n -> true, d -> d>=1500 && d<=2000)
				);
			}
		};
	}

	@Override
	public Diet lowCarb() {
		return new AbstractDiet() {

			@Override
			protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> getConstraints() {
				return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
						new Pair<>(n -> true, d -> d>=1000 && d<=1500),
						new Pair<>(n -> n == CARBS , d -> d<=300)
				);
			}
		};
	}

	@Override
	public Diet highProtein() {
		return new AbstractDiet() {

			@Override
			protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> getConstraints() {
				return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
						new Pair<>(n -> true, d -> d>=2000 && d<=2500),
						new Pair<>(n -> n == CARBS , d -> d<=300),
						new Pair<>(n -> n == PROTEINS , d -> d>=1300)
				);
			}
		};
	}

	@Override
	public Diet balanced() {
		return new AbstractDiet() {

			@Override
			protected Set<Pair<Predicate<Nutrient>, Predicate<Double>>> getConstraints() {
				return Set.<Pair<Predicate<Nutrient>, Predicate<Double>>>of(
						new Pair<>(n -> true, d -> d>=1600 && d<=2000),
						new Pair<>(n -> n == CARBS , d -> d>=600),
						new Pair<>(n -> n == PROTEINS , d -> d>=600),
						new Pair<>(n -> n == FAT , d -> d>=400),
						new Pair<>(n -> n == FAT || n == PROTEINS, d -> d<=1100)
				);
			}
		};
	}

}
