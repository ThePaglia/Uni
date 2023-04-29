package ex2015.a01c.sol1;

import java.util.*;
import java.util.stream.*;


public class MultiMapImpl<K,V> implements MultiMap<K, V> {

	private Map<K,Set<V>> mmap = new HashMap<>();
		
	private void prepareAddition(K key){
		this.mmap.merge(key, new HashSet<>(), (x,y)->x);
	}
		
	@Override
	public void add(K key, V value) {
		this.prepareAddition(key);
		this.mmap.get(key).add(value);
	}

	@Override
	public void add(K key, Iterable<V> values) {
		this.prepareAddition(key);
		final Set<V> set = this.mmap.get(key);
		values.forEach(v -> set.add(v));
	}

	@Override
	public Set<V> get(K key) {
		if (mmap.containsKey(key)){
			return new HashSet<>(mmap.get(key));
		}
		return new HashSet<>();
	}

	@Override
	public Set<Pair<K, V>> entrySet() {
		return this.mmap.keySet().stream().flatMap(k->this.mmap.get(k).stream().map(v->new Pair<>(k,v))).collect(Collectors.toSet());
	}

	@Override
	public Set<K> keys() {
		return this.mmap.keySet();
	}
}
