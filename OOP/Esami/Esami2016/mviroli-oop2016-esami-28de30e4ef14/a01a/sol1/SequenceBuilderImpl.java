package ex2016.a01a.sol1;

import java.util.*;


public class SequenceBuilderImpl<X> implements SequenceBuilder<X> {

    private final List<X> list = new ArrayList<X>();
    private boolean done = false;
    
    @Override
    public void addElement(X x) {
        this.list.add(x);
    }
    
    @Override
    public void removeElement(int position) {
        this.list.remove(position);   
    }

    @Override
    public void reverse() {
        Collections.reverse(this.list);
    }

    @Override
    public void clear() {
        this.list.clear();
    }
    
    @Override
    public Optional<Sequence<X>> build() {
        return this.buildWithFilter(x -> true);
    }
    
    @Override
    public Optional<Sequence<X>> buildWithFilter(Filter<X> filter) {
        if (this.done || !this.list.stream().allMatch(filter::check)) {
            return Optional.empty();
        }
        this.done = true;
        return Optional.of(new SequenceImpl<X>(this.list));
    }
    
    @Override
    public <Y> SequenceBuilder<Y> mapToNewBuilder(Mapper<X, Y> mapper) {
        final SequenceBuilder<Y> builder = new SequenceBuilderImpl<>();
        this.list.forEach(x -> builder.addElement(mapper.transform(x)));
        return builder;
    }

}
