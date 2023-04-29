package ex2015.a02b.e1;

import java.util.*;

/**
 * An interface modelling a restaurant menu
 *
 */
public interface Menu {

    // a menu is made of dishes
    interface Dish {

        // dish unique name
        String getName();

        // cost in euro cents
        int getCost();
        
        // a serving temperature 
        Temperature getTemperature();
        
        // optionally a string describing an award or price
        Optional<String> awarded();
    }
    
    enum Temperature {
        FROZEN, COLD, NORMAL, HOT; 
    }

    // A factory to create a dish with no award (need positive cost and non-null arguments)
    Dish createDish(String name, int cost, Temperature temperature);
    
    // A factory to create a dish with non-null award (need positive cost and non-null arguments)
    Dish createDish(String name, int cost, Temperature temperature, String award);
    
    // Adds the dish to the menu: throws IllegalArgumentException if dish name already exists
    void add(Dish d);
    
    // Returns overall menu cost
    int getOverallCost();
    
    // Returns the set of dish names
    Set<String> getDishNames();
       
    // Returns a map associating to each serving temperature, the list of dishes 
    Map<Temperature,List<Dish>> getMapByTemperature();
}