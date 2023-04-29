package a03b.sol1;

import java.util.*;
import java.util.stream.*;

import a03b.sol1.PostOffice.Operation;

public class PostOfficeFactoryImpl implements PostOfficeFactory {
	
	// This policy is used to state, given a previous operation at a desk, what could be the next one
	interface NextPolicy {
		Optional<Operation> next(Operation previous);
	}
	
	private PostOffice fromPolicy(NextPolicy policy, int desksSize) {
		return new PostOffice() {
			
			private int ticketCount;
			private List<Optional<Operation>> next = new ArrayList<>() {{
				for (int i=0; i<desksSize; i++) {
					add(Optional.empty());
				}
			}};
			private Map<Integer,Operation> waiting = new HashMap<>();
			private List<Optional<Pair<Integer,Operation>>> served = new ArrayList<>() {{
				for (int i=0; i<desksSize; i++) {
					add(Optional.empty());
				}
			}};

			@Override
			public int getTicket(Operation operation) {
				this.waiting.put(this.ticketCount,operation);
				return this.ticketCount++;
			}
			
			@Override
			public List<Integer> peopleWaiting() {
				return this.waiting.keySet().stream().sorted().collect(Collectors.toList());
			}

			@Override
			public Optional<Operation> deskState(int desk) {
				return this.served.get(desk).map(Pair::getY);
			}

			@Override
			public void goToDesk(int ticket, int desk) {
				if (ticket != waiting.keySet().stream().mapToInt(i->i).min().getAsInt()) {
					throw new IllegalStateException("not the next in line..");
				}
				if (this.served.get(desk).isPresent()) {
					throw new IllegalStateException("desk busy");					
				}
				if (this.next.get(desk).isPresent() && 
					this.next.get(desk).get()!= this.waiting.get(ticket)) {
					throw new IllegalStateException("counter can't be used for this operation");					
				}
				var p = new Pair<>(ticket, this.waiting.get(ticket));
				this.waiting.remove(ticket);
				this.served.set(desk, Optional.of(p));
			}

			@Override
			public void deskReleased(int desk) {
				if (this.served.get(desk).isEmpty()) {
					throw new IllegalStateException();		
				}
				this.next.set(desk, policy.next(this.served.get(desk).get().getY()));
				this.served.set(desk, Optional.empty());
			}

			@Override
			public void exitOnWait(int ticket) {
				if (!this.waiting.containsKey(ticket)) {
					throw new IllegalStateException();
				}
				this.waiting.remove(ticket);
			}
			
		};
	}

	@Override
	public PostOffice createFlexible(int desksSize) {
		return this.fromPolicy(o->Optional.empty(),desksSize);
	}

		@Override
	public PostOffice createAlternating(int desks) {
		return this.fromPolicy(o->Optional.of(o==Operation.SEND ? Operation.RECEIVE : Operation.SEND),desks);
	}

}
