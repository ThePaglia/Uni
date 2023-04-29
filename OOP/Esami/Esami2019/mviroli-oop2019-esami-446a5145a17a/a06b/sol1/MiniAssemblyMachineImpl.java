package a06b.sol1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MiniAssemblyMachineImpl implements MiniAssemblyMachine {
	
	interface Instruction {
		Pair<Optional<Integer>,List<Integer>> exec(int counter, List<Integer> registers);
	}	
	
	private List<Instruction> program = new LinkedList<>();
	
	
	@Override
	public void inc(int register) {
		Instruction i = (c,r) -> { r.set(register, r.get(register)+1); return new Pair<>(Optional.of(c+1), r);};
		program.add(i);
	}

	@Override
	public void dec(int register) {
		Instruction i = (c,r) -> { r.set(register, r.get(register)-1); return new Pair<>(Optional.of(c+1), r);};
		program.add(i);
	}

	@Override
	public void jnz(int register, int target) {
		Instruction i = (c,r) -> new Pair<>(Optional.of(r.get(register)!=0? target: c+1), r);
		program.add(i);
	}
	
	@Override
	public void ret() {
		Instruction i = (c,r) -> new Pair<>(Optional.empty(),r);
		program.add(i);
	}

	@Override
	public 	List<Integer> execute(List<Integer> registers){
		return execAt(0, new ArrayList<>(registers)).getY(); // Note defensive copy
	}
		
	private Pair<Optional<Integer>,List<Integer>> execAt(int line, List<Integer> registers) {
		if (program.size()<=line) {
			throw new IllegalStateException();
		}
		var res = program.get(line).exec(line, registers);
		if (res.getX().isEmpty()) {
			return res;
		} else {
			return execAt(res.getX().get(),res.getY());
		}
	}
	 
}
