package it.unibo.apice.oop.p17lambda.first;

import java.util.Objects;

public class Person {
    
    private final String name;
    private final int birthYear;
    private final boolean married;
  
    public Person(final String name, final int birthyear, final boolean married){
    	this.name = name;		
    	this.birthYear = birthyear;
    	this.married = married;
    }
    
    public String getName() {
		return name;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public boolean isMarried() {
		return married;
	}

	public String toString(){
    	return this.name + ":" + this.birthYear + ":" + 
    	       (this.married ? "spos" : "non-spos");
    }

	@Override
	public int hashCode() {
		return Objects.hash(name, birthYear, married);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		return Objects.equals(name, other.name) && birthYear == other.birthYear && married == other.married;
	}
}