package a06.sol1;

import java.util.List;

public class BTreeFactoryImpl implements BTreeFactory {
	
	@Override
	public <L> BTree<L> leaf(L value) {
		return new Leaf<>(value);			
	}

	@Override
	public <L> BTree<L> compose(BTree<L> left, BTree<L> right) {
		return new Cons<>(left,right);
	}

	@Override
	public <L> BTree<L> nested(List<L> leafs) {
		var iterator = leafs.iterator();
		BTree<L> tree = leaf(iterator.next());
		while (iterator.hasNext()) {
			tree = compose(leaf(iterator.next()),tree);
		}
		return tree;
	}

}
