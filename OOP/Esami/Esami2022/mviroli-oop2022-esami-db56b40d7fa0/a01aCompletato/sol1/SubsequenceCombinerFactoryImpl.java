package a01a.sol1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    private <X,Y> SubsequenceCombiner<X,Y> generic(Predicate<List<X>> ready, Function<List<X>,Y> mapper){
        return new SubsequenceCombiner<X,Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                final List<Y> outList = new ArrayList<>();
                final List<X> tempList = new ArrayList<>();
                for (final var x: list){
                    tempList.add(x);
                    if (ready.test(tempList)){
                        outList.add(mapper.apply(tempList));
                        tempList.clear();
                    }
                }
                if (!tempList.isEmpty()){
                    outList.add(mapper.apply(tempList));
                }
                return outList;
            }
        };
    } 

    @Override
    public SubsequenceCombiner<Integer,Integer> tripletsToSum() {
        return generic(l -> l.size() == 3, l -> l.stream().mapToInt(i -> i).sum());
    }

    @Override
    public <X> SubsequenceCombiner<X,List<X>> tripletsToList() {
        return generic(l -> l.size() == 3, ArrayList::new);
    }
    
    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return generic(l -> !l.isEmpty() && l.get(l.size()-1) == 0, 
            l -> l.get(l.size()-1) == 0 ? l.size() - 1 : l.size());
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return generic(l -> l.size() == 1, l -> function.apply(l.get(0)));
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return generic(l -> l.stream().mapToInt(i -> i).sum() >= threshold, ArrayList::new);
    }

}
