package a05a.sol1;

import java.util.*;

/**
 * An interface to model a factory for various kinds of IteratorOfLists
 */
public interface IteratorOfListsFactory {
	
	/**
	 * @param <E>
	 * @param e
	 * @return an iterator of lists of increasing size without a limit, all filled with element e, namely producing:
	 * {}, {e}, {e,e}, {e,e,e}, {e,e,e,e},...
	 */
	<E> IteratorOfLists<E> iterative(E e);
	
	/**
	 * @param <E>
	 * @param list {e1,..,en}
	 * @return an iterator of lists of increasing size without a limit, all filled with elements taken from
	 * list, repetitively, namely producing:
	 * {}, {e1}, {e1,e2},.., {e1,e2,..,en}, {e1,e2,..,en,e1}, {e1,e2,..,en,e1,e2},..., {e1,e2,..,en,e1,e2,..,en},..
	 */
	<E> IteratorOfLists<E> iterativeOnList(List<E> list);
	
	/**
	 * @param <E>
	 * @param preamble (e1,..,en)
	 * @return an iterator lists starting with preamble, then with elements e by increasing size each time,
	 * namely producing:
	 * {e1,..,en}, {e1,..,en,e}, {e1,..,en,e,e}, ..., {e1,..,en,e,e,...,e},...
	 */
	<E> IteratorOfLists<E> iterativeWithPreamble(E e, List<E> preamble);
		
}
