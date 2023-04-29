package ex2014.a03.sol2;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class SelectorModelImpl implements SelectorModel {
	
	private static final String[] commands = {"<<","<","0",">",">>"};
	private static final int MIN = -20;
	private static final int MAX = 20;
	private static final int BIGDELTA = 5;
	private static final int SMALLDELTA = 1;
	private static final List<Predicate<Integer>> preds = Arrays.asList(
			i->i>=MIN+BIGDELTA,
			i->i>=MIN+SMALLDELTA,
			i->i!=0,
			i->i<=MAX-SMALLDELTA,
			i->i<=MAX-BIGDELTA);  
	private static final List<Function<Integer,Integer>> funs = Arrays.asList(
			i->i-BIGDELTA,
			i->i-SMALLDELTA,
			i->0,
			i->i+SMALLDELTA,
			i->i+BIGDELTA);  
	private int value = 0;
	private List<String> log = new ArrayList<>();
	
	@Override
	public int size() {
		return commands.length+1;
	}

	@Override
	public List<String> currentNames() {
		return Stream.concat(Stream.of(commands),
							 Stream.of(String.valueOf(value)))
					 .collect(Collectors.toList());
	}

	@Override
	public List<Boolean> currentEnabling() {
		return Stream.concat(preds.stream().map(p->p.test(value)),
							 Stream.of(true))
					 .collect(Collectors.toList());
	}

	@Override
	public void hit(int elem) {
		if (elem == commands.length){
			this.log.add("Exit:"+value);
			System.out.println(result());
			System.exit(0);
		}
		log.add(commands[elem]);
		this.value = funs.get(elem).apply(this.value);
	}

	@Override
	public String result() {
		return this.log.toString();
	}
	
}
