package ex2016.a03b.sol1;

import java.util.*;
import static ex2016.a03b.sol1.TimeUtils.*;

public class ClockImpl implements Clock {
    
    private Time time;
    private Map<Time,List<Runnable>> observers = new HashMap<>();
    
    public ClockImpl(Time time){
        this.time = time;
    }

    @Override
    public Time getTime() {
        return this.time;
    }
    
    private Time getAdvancedTime(int seconds){
        return new TimeImpl(time.getSecondsFromMidnight()+seconds);
    }

    @Override
    public void tick() {
        this.time = this.getAdvancedTime(1);
        notifyObservers();
    }
    
    private void notifyObservers(){
        this.observers.getOrDefault(time, new LinkedList<>()).forEach(Runnable::run);
    }

    @Override
    public void registerAlarmObserver(Time time, Runnable observer) {
        final List<Runnable> oneObserver = new LinkedList<>(Arrays.asList(observer));
        this.observers.merge(time, oneObserver, (v,v1)->{v.addAll(v1); return v;});
    }

    @Override
    public void registerHoursDeadlineObserver(int hours, Runnable observer) {
        this.registerAlarmObserver(getAdvancedTime(hours*N_SECONDS*N_MINUTES), observer);
    }

    @Override
    public void registerMinutesDeadlineObserver(int minutes, Runnable observer) {
        this.registerAlarmObserver(getAdvancedTime(minutes*N_SECONDS), observer);
    }

    @Override
    public void registerSecondsDeadlineObserver(int seconds, Runnable observer) {
        this.registerAlarmObserver(getAdvancedTime(seconds), observer);
    }
}
