package a01b.sol1;

import java.util.Iterator;
import java.util.Optional;

public class TraceImpl<X> implements Trace<X> {
	
	private final Iterator<Event<X>> it;
	
	// package-private
	TraceImpl(Iterator<Event<X>> it){
		this.it = it;
	}
	
	@Override
	public Optional<Event<X>> nextEvent() {
		return  it.hasNext() ? Optional.of(it.next()) : Optional.empty();
	}

	@Override
	public void skipAfter(int time) {
		while (it.hasNext() && it.next().getTime()<time) {}
	}

	@Override
	public Trace<X> combineWith(Trace<X> trace) {
		return new TraceImpl<X>(new Iterator<Event<X>>() {
			Optional<Event<X>> e1 = nextEvent();
			Optional<Event<X>> e2 = trace.nextEvent();
			@Override
			public boolean hasNext() {
				return e1.isPresent() || e2.isPresent();
			}
			@Override
			public Event<X> next() {
				Event<X> e;
				if (!e2.isPresent() || e1.isPresent() && e1.get().getTime()<e2.get().getTime()) {
					e = e1.get();
					e1 = nextEvent();
				} else {
					e = e2.get();
					e2 = trace.nextEvent();
				}
				return e;
			}
		});
	}

	@Override
	public Trace<X> dropValues(X value) {
		return new TraceImpl<X>(new Iterator<Event<X>>() {
			Optional<Event<X>> e = nextValid();
			private Optional<Event<X>> nextValid(){
				Optional<Event<X>> e2 = nextEvent();
				return e2.isPresent() && e2.get().getValue().equals(value) ? nextValid() : e2; 
			}
			@Override
			public boolean hasNext() {
				return e.isPresent();
			}
			@Override
			public Event<X> next() {
				Event<X> e2 = e.get();
				e = nextValid();
				return e2;
			}
		});
	}

	@Override
	public Iterator<Event<X>> iterator() {
		return this.it;
	}
		
}
