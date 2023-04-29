class Person { // dichiarazione classe
	static int currentYear = new java.util.Date().getYear();
	String name;
	int birth;
	boolean married;

	Person(String name) {
		this.name = name;
		this.birth = Person.currentYear;
		this.married = false;
	}

	Person(String name, int birth) {
		this.name = name;
		this.birth = birth;
		this.married = false;
	}

	Person(String name, int birth, boolean married) {
		this.name = name;
		this.birth = birth;
		this.married = married;
	}
}
