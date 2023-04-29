package ex2015.a01c.sol1;

public interface MapFactory {
	
	/**
	 * @return an empty multi-map
	 */
	<K,V> MultiMap<K,V> empty();
	
	/**
	 * Given a multi-map, it creates a *new* multimap with same content,
	 * and that won't be modify from now, neither by changing the map
	 * provided as argument, nor by trying to call methods add, which
	 * have to raise an UnsupportedOperationException.
	 * 
	 * @return an unmodifiable copy of mmap
	 */
	<K,V> MultiMap<K,V> unmodifiable(MultiMap<K,V> mmap);

}
