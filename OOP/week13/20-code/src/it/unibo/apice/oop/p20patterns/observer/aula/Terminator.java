package it.unibo.apice.oop.p20patterns.observer.aula;

public class Terminator implements Observer {

    @Override
    public void notifyStringInserted(String s) {
        if (s.equals("exit")){ 
            System.exit(1);
        }
    }

}
