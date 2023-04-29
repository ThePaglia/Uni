package ex2016.a03b.sol1;

import static ex2016.a03b.sol1.TimeUtils.*;

public class TimeImpl implements Time {
    
    private final int secondsFromMidnight;
    
    // Package private, since it is used by Clock only
    TimeImpl(int secondsFromMidnight){
        this.secondsFromMidnight = secondsFromMidnight % (N_SECONDS*N_MINUTES*N_HOURS);
    }
    
    public TimeImpl(int hours, int minutes, int seconds) {
        if (!correctHours(hours) || !correctMinutes(minutes) || !correctSeconds(seconds)){
            throw new IllegalArgumentException();
        }
        this.secondsFromMidnight = seconds + N_SECONDS*minutes + N_SECONDS*N_MINUTES*hours;
    }

    @Override
    public int getHours() {
        return this.secondsFromMidnight / (N_SECONDS*N_MINUTES);
    }

    @Override
    public int getMinutes() {
        return (this.secondsFromMidnight % (N_SECONDS*N_MINUTES)) / N_SECONDS;
    }

    @Override
    public int getSeconds() {
        return this.secondsFromMidnight % N_SECONDS;
    }

    @Override
    public int getSecondsFromMidnight() {
        return this.secondsFromMidnight;
    }
    
    private static String intLabel(int i){
        return (i<10 ? "0" : "")+i;
    }
    
    @Override
    public String getLabel24() {
        return intLabel(getHours())+":"+intLabel(getMinutes())+":"+intLabel(getSeconds());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + secondsFromMidnight;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TimeImpl other = (TimeImpl) obj;
        if (secondsFromMidnight != other.secondsFromMidnight) {
            return false;
        }
        return true;
    }

}
