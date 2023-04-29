package ex2014.a02b.sol1;

import java.util.*;
import java.util.stream.*;

public class TicketDispenserImpl<A> implements TicketDispenser<A>{
	
	private List<A> waiting = new ArrayList<>();
	private int currentTicket = 0;
	private int nextTicketToServe = 0;
	private List<Optional<A>> served = new ArrayList<>();
	
	public TicketDispenserImpl(int size){
		for (int i=0;i<size;i++){
			served.add(Optional.empty());
		}
	}

	@Override
	public int getCashDeskSize() {
		return this.served.size();
	}

	@Override
	public int getNextAvailableTicket() {
		return this.currentTicket;
		
	}

	@Override
	public int giveNextTicketToAgent(A a) {
		if (this.waiting.contains(a) || 
			this.served.stream().filter(Optional::isPresent).map(Optional::get).anyMatch(a2->a2.equals(a))){
			throw new IllegalArgumentException();
		}
		waiting.add(a);
		return this.currentTicket++;
	}

	@Override
	public void useCashDesk(int desk){
		System.out.println(this.served.get(desk));
		if (this.waiting.size()==0 || this.served.get(desk).isPresent()){
			throw new IllegalStateException();
		}
		final A a = this.waiting.remove(0);
		this.served.set(desk,Optional.of(a));
		this.nextTicketToServe++;
	}

	@Override
	public void releaseCashDesk(int desk) {
		this.served.set(desk,Optional.empty());
		
	}

	@Override
	public boolean isCashDeskServing(int desk) {
		return this.served.get(desk).isPresent();
	}

	@Override
	public A getAgentUsingCashDesk(int desk) {
		if (!this.isCashDeskServing(desk)){
			throw new NoSuchElementException();
		}
		return this.served.get(desk).get();
	}

	@Override
	public int getNextServingTicket() {
		return this.nextTicketToServe;
	}

	@Override
	public Set<A> allWaiting() {
		return new HashSet<>(this.waiting);
	}

	@Override
	public Set<A> allCurrentlyServed() {
		return this.served.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
	}

}
