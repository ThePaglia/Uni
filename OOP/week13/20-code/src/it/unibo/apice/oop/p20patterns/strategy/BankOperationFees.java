package it.unibo.apice.oop.p20patterns.strategy;

@FunctionalInterface
public interface BankOperationFees {
	
	int fee(int operationAmount);
}
