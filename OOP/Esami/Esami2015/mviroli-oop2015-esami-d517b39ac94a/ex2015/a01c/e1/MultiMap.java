package ex2015.a01c.e1;

import java.util.*;

/**
 * Represents a multi-map from K to V, namely, a map where keys can have more values associated to it (in no order).
 * It is intended that method get is O(1), and that defensive copies are to be made where needed, so that a multi-map
 * can be modified only through methods add.
 */
public interface MultiMap<K,V> {
	
	/**
	 * Adds a new association from key to value.
	 * If one already exists nothing happens.
	 */
	void add(K key, V value);
		
	/**
	 * Adds new associations from key to all the elements of values.
	 * If some already exists nothing happens.
	 */
	void add(K key, Iterable<V> values);
		
	/**
	 * Gets the set of all values associated to key.
	 * Should be constant-time.
	 */
	Set<V> get(K key);
	
	/**
	 * Gets the set of all k-v entries in the multi-map.
	 */
	Set<Pair<K,V>> entrySet();
	
	/**
	 * Gets the set of all keys in the multi-map.
	 */
	Set<K> keys();

}
