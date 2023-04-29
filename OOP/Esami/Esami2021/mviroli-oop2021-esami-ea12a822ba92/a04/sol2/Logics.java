package a04.sol2;

import java.util.Optional;
import java.util.function.IntBinaryOperator;

public interface Logics{
	
	boolean isCellNumber(int x, int y);
	
	Optional<Operation> getCellAsOperation(int x, int y);
	
	Optional<Integer> getCellAsNumber(int x, int y);
	
	boolean hit(int x, int y);
	
	Optional<Integer> computeResult();
	
	enum Operation {
		PLUS("+",(x,y)->x+y), MINUS("-",(x,y)->x-y), TIMES("*",(x,y)->x*y);
		
		private String name;
		private IntBinaryOperator operator;
		
		private Operation(String name, IntBinaryOperator operator) {
			this.name = name;
			this.operator = operator;
		}

		public String getName() {
			return name;
		}

		public IntBinaryOperator getOperator() {
			return operator;
		}
	}    
}
