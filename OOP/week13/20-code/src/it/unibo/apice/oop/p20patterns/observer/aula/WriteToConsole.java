package it.unibo.apice.oop.p20patterns.observer.aula;

public class WriteToConsole implements Observer {

    @Override
    public void notifyStringInserted(String s) {
        System.out.println(s);
    }

}
