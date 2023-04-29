package ex2014.a01.sol2;

import java.util.*;
import java.util.stream.*;

public class FibonacciFormStrategy implements FormStrategy{
	
	private int size;
	
	public FibonacciFormStrategy(final int size){
		this.size = size;
	}
	
	@Override
	public List<String> fieldNames(){
		return IntStream.range(0,size)
					    .mapToObj(String::valueOf)
					    .collect(Collectors.toList());
	}
	
	@Override
	public boolean fieldsFilter(final Map<String, String> values) {
		return IntStream.range(0, this.size)
		         		.allMatch(i->values.get(String.valueOf(i)).equals(String.valueOf(fibonacci(i))));
	}

	@Override
	public void onResult(final Map<String, String> values) {
		IntStream.range(0, this.size)
				 .mapToObj(String::valueOf)
				 .map(values::get)
				 .forEach(System.out::println);
		System.exit(0);
	}
	
	
	private long fibonacci(final int i){
		switch (i){
		case 0: return 1;
		case 1: return 1;
		default: return fibonacci(i-1)+fibonacci(i-2);
		}
	}
}
