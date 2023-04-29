package pr2016.a06.sol1;

import java.util.*;
import java.util.stream.*;

public class IntIteratorsFactoryImpl implements IntIteratorsFactory {

    private Iterator<Integer> fromStream(Stream<Integer> stream){
        return stream.iterator();
    }
    
    @Override
    public Iterator<Integer> fromList(List<Integer> list){
        return list.iterator();
    }
    
    @Override
    public Iterator<Integer> empty() {
        return this.fromStream(Stream.empty());
    }
    
    private Stream<Integer> fromToStream(int start, int end){
        return IntStream.rangeClosed(start, end).boxed();
    }
    
    @Override
    public Iterator<Integer> fromTo(int start, int end){
        return this.fromStream(fromToStream(start, end));
    }

    @Override
    public Iterator<Integer> fromToIndefinitely(int start, int end) {
        return java.util.stream.Stream.<Integer>generate(()->0)
                .<Integer>flatMap(i->fromToStream(start, end))
                .iterator();
    }
    
    @Override
    public Iterator<Integer> zeros(){
        return this.fromToIndefinitely(0, 0);
    }
    
    @Override
    public Iterator<Integer> alternateOneAndZeroIndefinitely() {
        return fromToIndefinitely(0,1);
    }

}
