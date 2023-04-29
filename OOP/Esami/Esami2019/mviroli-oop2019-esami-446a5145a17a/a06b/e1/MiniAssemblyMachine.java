package a06b.e1;

import java.util.List;

/**
 * This interface models a register-based machine executing programs written in a 
 * "mini assembly" language. This language has instructions with just 4 possible opcodes.
 * After the program is inserted (via methods inc, dec, jnz and ret), it can be executed
 * passing a list of register values, and receiving the new list as output. We do not consider
 * the cases in which computation does not terminate.
 * Initially the program has 0 instructions.   
 */
public interface MiniAssemblyMachine {
	
	/**
	 * Adds at the end of current program an INC instruction
	 * @param register, is the index of the register to increment
	 */
	void inc(int register);
	
	/**
	 * Adds at the end of current program a DEC instruction
	 * @param register, is the index of the register to decrement
	 */
	void dec(int register);
	
	/**
	 * Adds at the end of current program a JNZ instruction (Jump If Not Zero).
	 * When executed, if the @register has value 0 nothing is done, otherwise we jump to the @target-th instruction
	 * @param register
	 * @param target
	 */
	void jnz(int register, int target);
	
	/**
	 * Adds at the end of current program a RET instruction, which just stops execution.
	 */
	void ret();
	
	/**
	 * Performs a full execution of current program. 
	 * @param registers, is the input state of registers (note this input also says how many registers will be used)
	 * @return the output state of registers
	 * @throws IllegalStateException if next instruction is not available (OPTIONAL IN THIS EXAM)
	 * @throws IndexOutOfBoundsException if the register does not exist (OPTIONAL IN THIS EXAM)
	 */
	List<Integer> execute(List<Integer> registers);
	
}
