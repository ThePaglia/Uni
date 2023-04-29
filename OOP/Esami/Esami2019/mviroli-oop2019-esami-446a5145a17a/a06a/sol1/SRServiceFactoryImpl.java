package a06a.sol1;

import java.util.*;

public class SRServiceFactoryImpl implements SRServiceFactory {
	
	enum State {
		ENTERED, GO_RECEIVE, GO_SEND
	}
	
	// This policy is used to state weather, given certain receiver and senders, we can allow a new receive/send
	interface Policy{
		boolean canGo(boolean receiver, Set<Integer> receivers, Set<Integer> senders);
	}
	
	private SRService byPolicy(Policy p) {
		return new SRService() {
			private Map<State,Set<Integer>> clients = new EnumMap<>(State.class) {{
				put(State.ENTERED, new HashSet<>());
				put(State.GO_RECEIVE, new HashSet<>());
				put(State.GO_SEND, new HashSet<>());
			}};
			private int counterId;
			
			@Override
			public int enter() {
				this.clients.get(State.ENTERED).add(this.counterId);
				return this.counterId++;
			}
			
			private void go(int id, boolean receive, State target) {
				if (!clients.get(State.ENTERED).contains(id)) {
					throw new IllegalStateException();
				}
				if (!p.canGo(receive, clients.get(State.GO_RECEIVE), clients.get(State.GO_SEND))) {
					throw new IllegalStateException();
				}
				clients.get(State.ENTERED).remove(id);
				clients.get(target).add(id);
			}
			@Override
			public void goReceive(int id) {
				this.go(id, true, State.GO_RECEIVE);
			}
			
			@Override
			public void goSend(int id) {
				this.go(id, false, State.GO_SEND);
			}
			
			@Override
			public void exit(int id) {
				if (!clients.get(State.GO_RECEIVE).contains(id) && !clients.get(State.GO_SEND).contains(id)) {
					throw new IllegalStateException();
				}
				clients.get(State.GO_SEND).remove(id);
				clients.get(State.GO_RECEIVE).remove(id);
			}
		};
	}

	@Override
	public SRService createMaximumAccess() {
		return byPolicy( (b,receivers,senders) -> true ); // can always access
	}

	@Override
	public SRService createWithNoConcurrentAccess() { // true if none is accessing
		return byPolicy( (b,receivers,senders) -> receivers.isEmpty() && senders.isEmpty() );
	}

	@Override
	public SRService createManyReceiveAndMaxTwoSend() {
		return byPolicy( (b,receivers,senders) -> b || senders.size()<=1);
	}

}
