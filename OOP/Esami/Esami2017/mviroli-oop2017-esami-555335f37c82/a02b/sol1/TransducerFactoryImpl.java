package a02b.sol1;

public class TransducerFactoryImpl implements TransducerFactory {

	@Override
	public <X> Transducer<X, String> makeConcatenator(int inputSize) {
		return new TransducerImpl<>(inputSize,l -> l.stream().map(Object::toString).reduce((x,y)->x+y).get());
	}

		@Override
	public Transducer<Integer, Integer> makePairSummer() {
		return new TransducerImpl<>(2,l -> l.stream().reduce((x,y)->x+y).get());
	}

}
