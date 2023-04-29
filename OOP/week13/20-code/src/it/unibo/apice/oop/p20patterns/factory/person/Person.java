package it.unibo.apice.oop.p20patterns.factory.person;

import java.util.Optional;

public interface Person {

    String getName();

    String getSurname();

    Optional<String> getCity();

    int getYear();

}