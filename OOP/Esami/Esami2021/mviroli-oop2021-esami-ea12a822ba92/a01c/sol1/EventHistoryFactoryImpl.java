package a01c.sol1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory {

	private <E> EventHistory<E> historyFromIterator(Iterator<Pair<Double,E>> iterator){
		return new EventHistory<>() {
			private Pair<Double,E> p = iterator.next();
			
			@Override
			public double getTimeOfEvent() {
				return p.get1();
			}

			@Override
			public E getEventContent() {
				return p.get2();
			}

			@Override
			public boolean moveToNextEvent() {
				if (iterator.hasNext()) {
					p = iterator.next();
					return true;
				}
				return false;
			}
		};
	}

	@Override
	public <E> EventHistory<E> fromMap(Map<Double, E> map) {
		return this.historyFromIterator(map
				.entrySet()
				.stream()
				.sorted( (p1,p2) -> p1.getKey()-p2.getKey() > 0.0 ? 1 : -1)
				.map( p -> new Pair<>(p.getKey(),p.getValue()))
				.iterator());
	}

	@Override
	public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
		return this.historyFromIterator(new Iterator<>() {

			@Override
			public boolean hasNext() {
				return times.hasNext() && content.hasNext();
			}

			@Override
			public Pair<Double, E> next() {
				return Optional.of(this.hasNext())
						.filter(b->b)
						.map(b->new Pair<>(times.next(),content.next()))
						.orElseThrow();
			}
		});
	}
	
	@Override
	public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
		return fromIterators(
				Stream.iterate(0.0, x -> x + Math.random()).limit(size).iterator(),
				Stream.generate(content).iterator());
	}

	@Override
	public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
		return fromIterators(Stream.iterate(initial, d->d+delta).iterator(), content.iterator());
	}

	@Override
	public EventHistory<String> fromFile(String file) throws IOException {
		Map<Double,String> map = new HashMap<>();
		try ( BufferedReader br = new BufferedReader(new FileReader(file))){
			String str = null;
			while ((str = br.readLine())!=null) {
				String[] s = str.split(":",2);
				map.put(Double.parseDouble(s[0]), s[1]);
			}
		}
		System.out.println(map);
		return this.fromMap(map);
	}
}
