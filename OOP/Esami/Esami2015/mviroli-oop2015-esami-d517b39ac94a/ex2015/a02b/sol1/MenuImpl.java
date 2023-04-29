package ex2015.a02b.sol1;

import java.util.*;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {
    
    private final Map<String,Dish> map = new HashMap<>();
    
    public MenuImpl(){}

    @Override
    public Dish createDish(String name, int cost, Temperature temperature) {
        return new DishBuilder().name(name).cost(cost).temperature(temperature).build();
    }

    @Override
    public Dish createDish(String name, int cost, Temperature temperature, String award) {
        return new DishBuilder().name(name).cost(cost).temperature(temperature).award(award).build();
    }

    @Override
    public void add(Dish d) {
        if (this.map.containsKey(Objects.requireNonNull(d).getName())){
            throw new IllegalArgumentException();
        }
        this.map.put(d.getName(),d);
    }

    @Override
    public int getOverallCost() {
        return this.map.values().stream().map(Dish::getCost).reduce(0, (x,y)->x+y);
    }
    
    @Override
    public Set<String> getDishNames() {
        return this.map.keySet();
    }

    @Override
    public Map<Temperature, List<Dish>> getMapByTemperature() {
        return this.map.values().stream().collect(Collectors.groupingBy(Dish::getTemperature, Collectors.toList()));
    }
    
    @Override
    public String toString(){
        return this.map.toString();
    }
    
    private class DishBuilder{
        private String name;
        private int cost;
        private Temperature temperature;
        private String award;
        
        public DishBuilder(){}
        
        public DishBuilder name(String s){
            this.name = s;
            return this;
        }
        
        public DishBuilder cost(int i){
            this.cost = i;
            return this;
        }
        
        public DishBuilder temperature(Temperature t){
            this.temperature = t;
            return this;
        }
        
        public DishBuilder award(String s){
            this.award = s;
            return this;
        }
        
        public Dish build(){
            if (cost <= 0 || this.name == null || this.temperature == null){
                throw new IllegalArgumentException();
            }
            return new DishImpl(this.name,this.cost,this.temperature,this.award);
        }
    }

    private class DishImpl implements Dish{
        
        private String name;
        private int cost;
        private Temperature temperature;
        private String award;
        
        private DishImpl(String name, int cost, Temperature temperature, String award) {
            super();
            this.name = name;
            this.cost = cost;
            this.temperature = temperature;
            this.award = award;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getCost() {
            return this.cost;
        }

        @Override
        public Temperature getTemperature() {
            return this.temperature;
        }

        @Override
        public Optional<String> awarded() {
            return Optional.of(this.award);
        }

        @Override
        public String toString() {
            return "D[name=" + name + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((award == null) ? 0 : award.hashCode());
            result = prime * result + cost;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DishImpl other = (DishImpl) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (award == null) {
                if (other.award != null)
                    return false;
            } else if (!award.equals(other.award))
                return false;
            if (cost != other.cost)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (temperature != other.temperature)
                return false;
            return true;
        }

        private MenuImpl getOuterType() {
            return MenuImpl.this;
        }
        
    }
}
