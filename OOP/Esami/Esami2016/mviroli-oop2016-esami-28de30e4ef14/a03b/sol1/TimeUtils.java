package ex2016.a03b.sol1;

/*
 * An helper class used by TimeImpl and ClockImpl
 */
public class TimeUtils {
    
    public static int N_SECONDS = 60;
    public static int N_MINUTES = 60;
    public static int N_HOURS = 24;
    
    public static boolean correctHours(int hours){
        return hours >= 0 && hours < N_HOURS;
    }
    
    public static boolean correctMinutes(int minutes){
        return minutes >= 0 && minutes < N_MINUTES;
    }
    
    public static boolean correctSeconds(int seconds){
        return seconds >= 0 && seconds < N_SECONDS;
    }
}
