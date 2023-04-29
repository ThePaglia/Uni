package a01b.sol1;

public class GridFactoryImpl implements GridFactory {

	@Override
	public <X> Grid<X> create(int rows, int columns) {
		return new GridImpl<>(rows,columns);
	}

}
