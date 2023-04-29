package ex2016.a02a.sol1;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {
    
    private final static Supplier<RuntimeException> ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER = ()->new IllegalArgumentException();
    private final static Supplier<RuntimeException> ILLEGAL_EXCEPTION_SUPPLIER = ()->new IllegalStateException();
        
    private Map<Q,List<T>> map = new HashMap<>();
    
    @Override
    public Set<Q> availableQueues() {
        return this.map.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {
        raiseException(this.map.containsKey(queue), ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER);
        this.map.put(queue, createEmptyList());
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        raiseException(!this.map.containsKey(queue), ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER);
        return this.map.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        raiseException(!this.map.containsKey(queue), ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER);
        this.map.get(queue).add(elem);
    }

    @Override
    public Optional<T> dequeue(Q queue) {
        raiseException(!this.map.containsKey(queue), ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER);
        List<T> list = this.map.get(queue);
        if (list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list.remove(0));
    }

    @Override
    public Map<Q, Optional<T>> dequeueOneFromAllQueues() {
        Map<Q,Optional<T>> res = new HashMap<>();
        for (Q queue: this.map.keySet()){
            res.put(queue, dequeue(queue));
        }
        return Collections.unmodifiableMap(res);
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        raiseException(!this.map.containsKey(queue), ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER);
        List<T> list = this.map.get(queue);
        this.map.put(queue, createEmptyList());
        return Collections.unmodifiableList(list);
    }
    
    @Override
    public void closeQueueAndReallocate(Q queue) {
        raiseException(!this.map.containsKey(queue), ILLEGAL_ARGUMENT_EXCEPTION_SUPPLIER);
        raiseException(this.map.size()==1, ILLEGAL_EXCEPTION_SUPPLIER);
        List<T> elems = this.dequeueAllFromQueue(queue);
        this.map.remove(queue);
        Q q = this.map.keySet().stream().findAny().get();
        elems.forEach(t -> this.enqueue(t, q));
    }

    @Override
    public Set<T> allEnqueuedElements() {
        return this.map.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }
    
    private static <T> List<T> createEmptyList(){
        return new LinkedList<>();
    }
    
    private void raiseException(boolean condition, Supplier<RuntimeException> supplier){
        if (condition){
            throw supplier.get();
        }
    }
    

}
