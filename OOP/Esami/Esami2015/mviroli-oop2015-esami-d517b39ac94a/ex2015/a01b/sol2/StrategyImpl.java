package ex2015.a01b.sol2;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class StrategyImpl implements Strategy {
	
	private final static char P = 'p';
	private final static char D = 'd';
	
    private enum Operation {
        OF_LENGTH_4("OF LENGTH 4",s -> s.length() == 4),
        SMALL_1000("< 1000",s -> Integer.parseInt(s.substring(0, s.length()-1))<1000),
        TRAILING_P("TRAILING P", s->s.charAt(s.length()-1) == P);

    	private final String name;
        private final Predicate<String> predicate;

        private Operation(final String name, final Predicate<String> p) {
            this.name = name;
            predicate = p;
        }
        
        public String getName() {
            return this.name;
        }

        public Predicate<String> getPredicate() {
            return this.predicate;
        }

    }

    private static final int BUTTONS = 100;
    
    private final Set<String> inputs = new HashSet<>();

    @Override
    public String doOperation(final String command) {
    	if (inputs.isEmpty()){
    		return "INVALID";
    	}
    	Operation op = Stream.of(Operation.values())
    			            .filter(o->o.getName().equals(command))
    			            .findFirst()
    			            .orElseThrow(()->new IllegalArgumentException());
    	return inputs.stream().allMatch(s -> op.getPredicate().test(s)) ? "TRUE" : "FALSE";
    }

    @Override
    public List<String> getInputs() {
    	return IntStream.range(0,BUTTONS).mapToObj(i-> i*i+ (i%2 == 0 ? String.valueOf(P) : String.valueOf(D))).collect(Collectors.toList());
    }

    @Override
    public List<String> getOperations() {
        return Arrays.stream(Operation.values()).map(Operation::getName).collect(Collectors.toList());
    }

    @Override
    public void reset() {
        this.inputs.clear();
    }

    @Override
    public void hitString(final String in) {
    	this.inputs.add(in);
    }

}
