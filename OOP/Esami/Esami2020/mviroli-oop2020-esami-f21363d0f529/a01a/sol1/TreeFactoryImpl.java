package a01a.sol1;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeFactoryImpl implements TreeFactory {

	@Override
	public <E> Tree<E> singleValue(E root) {
		return new TreeImpl<>(root,Collections.emptyList());
	}

	@Override
	public <E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2) {
		return new TreeImpl<>(root,Stream.of(child1,child2).collect(Collectors.toList()));
	}

	@Override
	public <E> Tree<E> oneLevel(E root, List<E> children) {
		return new TreeImpl<>(root,children.stream().map(this::singleValue).collect(Collectors.toList()));
	}

	@Override
	public <E> Tree<E> chain(E root, List<E> list) {
		if (list.isEmpty()) {
			return singleValue(root);
		}
		return new TreeImpl<>(root,Collections.singletonList(chain(list.get(0),list.subList(1, list.size()))));
	}
}
