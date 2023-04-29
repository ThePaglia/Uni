package a03a.sol1;

import java.util.*;

public class RWControllersFactoryImpl implements RWControllersFactory {
	
	enum State {
		ENTERED, GO_READ, GO_WRITE
	}
	
	// This policy is used to state weather, given certain readers and writers, we can allow a new read/write
	interface Policy{
		boolean canGo(boolean reader, Set<Integer> readers, Set<Integer> writers);
	}
	
	private RWController byPolicy(Policy p) {
		return new RWController() {
			private Map<State,Set<Integer>> processes = new EnumMap<>(State.class) {{
				put(State.ENTERED, new HashSet<>());
				put(State.GO_READ, new HashSet<>());
				put(State.GO_WRITE, new HashSet<>());
			}};
			private int counterId;
			
			@Override
			public int enter() {
				this.processes.get(State.ENTERED).add(this.counterId);
				return this.counterId++;
			}
			
			private void go(int id, boolean read, State target) {
				if (!processes.get(State.ENTERED).contains(id)) {
					throw new IllegalStateException();
				}
				if (!p.canGo(read, processes.get(State.GO_READ), processes.get(State.GO_WRITE))) {
					throw new IllegalStateException();
				}
				processes.get(State.ENTERED).remove(id);
				processes.get(target).add(id);
			}
			@Override
			public void goRead(int id) {
				this.go(id, true, State.GO_READ);
			}
			
			@Override
			public void goWrite(int id) {
				this.go(id, false, State.GO_WRITE);
			}
			
			@Override
			public void exit(int id) {
				if (!processes.get(State.GO_READ).contains(id) && !processes.get(State.GO_WRITE).contains(id)) {
					throw new IllegalStateException();
				}
				processes.get(State.GO_WRITE).remove(id);
				processes.get(State.GO_READ).remove(id);
			}
		};
	}

	@Override
	public RWController createPermissive() {
		return byPolicy( (b,readers,writers) -> true ); // can always access
	}

	@Override
	public RWController createOnlyRead() {
		return byPolicy( (b,readers,writers) -> b ); // true for readers (b=true)
	}

	@Override
	public RWController createMutualExclusion() { // true if none is accessing
		return byPolicy( (b,readers,writers) -> readers.isEmpty() && writers.isEmpty() );
	}

	@Override
	public RWController createManyReadersOrOneWriter() { // none is writing and, to read or none is reading
		return byPolicy( (b,readers,writers) -> writers.isEmpty() && (b || readers.isEmpty() ));
	} 
	

}
