package ex2015.a01a.sol2;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class ModelImpl implements Model {
	
	private static enum Op {
		SUM("SUM",(x,y)->x+y),
		MUL("MULTIPLY",(x,y)->x*y),
		MIN("MIN",(x,y)->x>y?y:x);
		
		private final String name;
		private final BinaryOperator<Integer> op;
		
		private Op(String name, BinaryOperator<Integer> op) {
			this.name = name;
			this.op = op;
		}

		public String getName() {
			return name;
		}

		public BinaryOperator<Integer> getOp() {
			return op;
		}
	} 
	
	private static final List<Integer> INT_VALUES = IntStream.range(0,100).mapToObj(x->2*x).collect(Collectors.toList());
	private static final List<String> STR_VALUES = INT_VALUES.stream().map(String::valueOf).collect(Collectors.toList());
	private static final List<String> OPS = Arrays.asList(Op.values()).stream().map(Op::getName).collect(Collectors.toList());
	
	private final List<Integer> selectedValues = new ArrayList<>();

	@Override
	public List<String> getValues() {
		return STR_VALUES;
	}

	@Override
	public List<String> getOperations() {
		return OPS;
	}

	@Override
	public void hitValue(String s) {
		final int val = INT_VALUES.get(STR_VALUES.indexOf(s));
		if (this.selectedValues.contains(val)){
			throw new IllegalStateException("can't hit twice");
		}
		this.selectedValues.add(val);
	}

	@Override
	public String execOperation(String s) {
		final Op op = Op.values()[OPS.indexOf(s)];
		if (this.selectedValues.isEmpty()){
			return "INVALID";
		}
		return this.selectedValues.stream().reduce(op.getOp()).get().toString();
	}

	@Override
	public void resetValues() {
		this.selectedValues.clear();
	}
}
