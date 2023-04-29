package ex2015.a01c.sol1;

import java.util.*;

public class MapFactoryImpl implements MapFactory {

	@Override
	public <K, V> MultiMap<K, V> empty() {
		return new MultiMapImpl<>();
	}

	@Override
	public <K, V> MultiMap<K, V> unmodifiable(MultiMap<K, V> mmap) {
		MultiMap<K,V> mmap2 = new MultiMapImpl<>();
		mmap.entrySet().forEach(p->mmap2.add(p.getX(), p.getY()));
		return new MultiMap<K,V>(){

			@Override
			public void add(K key, V value) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void add(K key, Iterable<V> values) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Set<V> get(K key) {
				return mmap2.get(key);
			}

			@Override
			public Set<Pair<K, V>> entrySet() {
				return mmap2.entrySet();
			}

			@Override
			public Set<K> keys() {
				return mmap2.keys();
			}
			
		};
	}


}
