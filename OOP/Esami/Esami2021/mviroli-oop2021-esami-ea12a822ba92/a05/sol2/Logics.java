package a05.sol2;

import java.util.Optional;
import java.util.function.BinaryOperator;

public interface Logics{
	
	boolean isCellValue(int x, int y);
	
	Optional<Operation> getCellAsOperation(int x, int y);
	
	Optional<Boolean> getCellAsValue(int x, int y);
	
	boolean hit(int x, int y);
	
	Optional<Boolean> computeResult();
	
	enum Operation {
		AND("AND",(x,y)-> x && y), OR("OR",(x,y)->x || y), XOR("XOR",(x,y)->x ^ y);
		
		private String name;
		private BinaryOperator<Boolean> operator;
		
		private Operation(String name, BinaryOperator<Boolean> operator) {
			this.name = name;
			this.operator = operator;
		}

		public String getName() {
			return name;
		}

		public BinaryOperator<Boolean> getOperator() {
			return operator;
		}
	}    
}
