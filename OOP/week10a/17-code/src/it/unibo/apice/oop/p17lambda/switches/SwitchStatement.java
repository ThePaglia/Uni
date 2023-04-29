package it.unibo.apice.oop.p17lambda.switches;

// Assumiamo: enum WorkDay { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY }
public class SwitchStatement {

    public static boolean isAGoodDay(WorkDay workday){
        boolean result = false;
        switch (workday){
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
                result = false;
                break;
            case THURSDAY:
            case FRIDAY:
                result = true;
        }
        return result;
    }

    public static void printWelcomeMessage(WorkDay workday){
        switch (workday){
            case MONDAY: System.out.println("Welcome from weekend!"); break;
            case FRIDAY: System.out.println("Have a nice weekend!"); break;
            default: System.out.println("Nothing to say");
        }
    }

    public static int fibonacci(int i){
        switch (i) {
            case 0: return 1;
            case 1: return 1;
            default: return fibonacci(i-1) + fibonacci(i-2);
        }
    }
}
