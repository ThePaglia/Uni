package ex2016.a04.sol2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelImpl implements Model {
    
    private static enum Action {
        LEFT, RIGHT;
        public String toString(){
            return this==LEFT ? "l" : "r";
        }
    }
    
    private static List<List<Action>> choices = Arrays.asList(
            Arrays.asList(Action.LEFT,Action.LEFT,Action.LEFT,Action.LEFT,Action.LEFT),
            Arrays.asList(Action.RIGHT,Action.RIGHT,Action.RIGHT),
            Arrays.asList(Action.LEFT,Action.RIGHT,Action.LEFT,Action.RIGHT)           
    );
    
    private static List<String> stringChoices = choices.stream().map(l -> l.toString()).collect(Collectors.toList());
    
    private int choice;
    private int position;
    
    @Override
    public List<String> choices() {
        return Collections.unmodifiableList(stringChoices);
    }

    @Override
    public void choose(String s) {
        this.choice = stringChoices.indexOf(s);
        this.position = 0;
    }
    
    private boolean hit(Action a){
        if (choices.get(choice).get(position)==a) {
            position++;
            return true;
        }
        position = 0;
        return false;
    }

    @Override
    public boolean hitLeft() {
        return hit(Action.LEFT);
    }

    @Override
    public boolean hitRight() {
        return hit(Action.RIGHT);
    }

    @Override
    public boolean shouldQuit() {
        return choices.get(choice).size()==position;
    }
}
