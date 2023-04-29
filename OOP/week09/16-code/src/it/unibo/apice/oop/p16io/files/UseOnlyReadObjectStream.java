package it.unibo.apice.oop.p16io.files;

import java.io.*;

public class UseOnlyReadObjectStream {

	public static void main(String[] args) throws Exception {

		// ostream2 -> bstream2 -> file2
		try (
			final InputStream file2 = new FileInputStream(UseFile.FILE_NAME);
			final InputStream bstream2 = new BufferedInputStream(file2);
			final ObjectInputStream ostream2 = new ObjectInputStream(bstream2);
		){
			System.out.println(ostream2.readInt());
			System.out.println(ostream2.readDouble());
			java.util.Date date = (java.util.Date)ostream2.readObject();
			System.out.println(date.toString()+ " "+date.getHours()); // carica il Date
			System.out.println(ostream2.readObject()); // carica la Persona
		}
	}
}
