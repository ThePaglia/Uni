package it.unibo.apice.oop.p20patterns.observer.auladone;

public class WriteToConsole implements Observer {

    @Override
    public void valueInserted(String s) {
        System.out.println(s);
    }

}
