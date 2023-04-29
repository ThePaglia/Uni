package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory{



    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<Integer,Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                List<Integer> res = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (i % 3 == 0) {
                        int end = i + 3 < list.size() ? i + 3 : list.size();
                        int sum = 0;
                        for (int j = i; j < end; j++) {
                            sum += list.get(j);
                        }
                        res.add(sum);
                    }
                }
                return res;
            }
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X,List<X>>() {

            @Override
            public List<List<X>> combine(List<X> list) {
                List<List<X>> result = new ArrayList<>();
                for (int i = 0; i < list.size(); i += 3) {
                    int endIndex = Math.min(i + 3, list.size());
                    result.add(list.subList(i, endIndex));
                }
                return result;
            }
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombiner<Integer,Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                List<Integer> res = new ArrayList<>();
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == 0) {
                        res.add(count);
                        count = 0;
                    } else {
                        count++;
                        if (i == list.size()-1) {
                            res.add(count);
                        }
                    }
                }
                return res;
            }
        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X,Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                List<Y> result = new ArrayList<>(list.size());
                for (X x : list) {
                    result.add(function.apply(x));
                }
                return result;
            }
        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<Integer,List<Integer>>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                List<List<Integer>> result = new ArrayList<>();
                List<Integer> currentSubseq = new ArrayList<>();
                int currentSum = 0;
                
                for (Integer element : list) {
                    currentSubseq.add(element);
                    currentSum += element;
                    
                    if (currentSum >= threshold) {
                        result.add(currentSubseq);
                        currentSubseq = new ArrayList<>();
                        currentSum = 0;
                    }
                }
                
                if (!currentSubseq.isEmpty()) {
                    result.add(currentSubseq);
                }
                
                return result;
            }    
        };
    }

}
