package a01a.sol1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SplitIteratorFactoryImpl implements SplitIteratorFactory {

	/* 
	 * The iterator should not cache future values!
	 */
	@Override
	public SplitIterator<Integer> fromRange(int start, int stop) {
		return new SplitIterator<Integer>(){
			
			private int current = start;

			@Override
			public Optional<Integer> next() {
				// The use of Optional.filter prevents a more verbose use of if 
				return Optional.of(this.current++).filter(c -> c <= stop);
			}

			@Override
			public SplitIterator<Integer> split() {
				int oldCurrent = this.current;
				this.current = (this.current+stop)/2;
				return fromRange(oldCurrent,this.current-1);
			}
		};
	}
	
	/* 
	 * You could have the NoSplit version of any Iterator. Hence, there's more quality if you solve the problem once
	 * and for all. This can be done by a decoration.
	 */
	@Override
	public SplitIterator<Integer> fromRangeNoSplit(int start, int stop) {
		return new NoSplitIteratorDecorator<>(fromRange(start,stop));
	}


	@Override
	public <X, Y> SplitIterator<Y> map(SplitIterator<X> si, Function<X, Y> mapper) {
		return new SplitIterator<Y>() {

			@Override
			public Optional<Y> next() {
				return si.next().map(mapper);
			}

			@Override
			public SplitIterator<Y> split() {
				return map(si.split(),mapper);
			}
			
		};
	}
	
	/* 
	 * A crucial idea to avoid repetitions here, is to implement fromList by fromRange thanks to map()
	 */
	@Override
	public <X> SplitIterator<X> fromList(List<X> list) {
		return map(fromRange(0,list.size()-1), list::get);
	}

	@Override
	public <X> SplitIterator<X> fromListNoSplit(List<X> list) {
		return new NoSplitIteratorDecorator<>(fromList(list));
	}

	@Override
	public <X> SplitIterator<X> excludeFirst(SplitIterator<X> si) {
		si.next();
		return new SplitIterator<X>() {

			@Override
			public Optional<X> next() {
				return si.next();
			}

			@Override
			public SplitIterator<X> split() {
				return si.split();
			}
			
		};
	}

}
