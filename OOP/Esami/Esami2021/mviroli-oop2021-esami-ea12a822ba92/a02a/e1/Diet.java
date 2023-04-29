package a02a.e1;

import java.util.Map;

/**
 * Models a diet regime, essentially with a method 'isValid' to check
 * if a selection of food is compliant with this diet
 */
public interface Diet {
	
	/**
	 * The three standard types of nutrients
	 */
	enum Nutrient {
		CARBS, PROTEINS, FAT
	}
	
	/**
	 * Add information about a new type of food ("pasta", "pollo", and so on) 
	 * 
	 * @param name is the unique name of the food
	 * @param nutritionMap gives a map from a nutrient to how many calories it brings with 100 grams of food
	 * (see method Test.fillProducts for its usage
	 */
	void addFood(String name, Map<Nutrient,Integer> nutritionMap);
	
	/**
	 * Given a selection of food for a day diet, it states if this is valid in current diet.
	 * Note that if 100 grams of 'pasta' give 50 calories of FAT (according to nutritionMap above),
	 * then 200 grams of pasta added in dietMap brings 100 calories.
	 * 
	 * @param dietMap, gives how many grams of each food are selected
	 * @return whether this selection of food is ok in this diet
	 */
	boolean isValid(Map<String, Double> dietMap);

}
