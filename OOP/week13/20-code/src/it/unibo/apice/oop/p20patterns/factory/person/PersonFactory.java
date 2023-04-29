package it.unibo.apice.oop.p20patterns.factory.person;

public interface PersonFactory {

    Person createBasic(String name, String surname);

    Person createAdvanced(String name, String surname, String city);

}