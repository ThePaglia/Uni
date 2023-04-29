package ex2014.a02b.sol2;

import java.util.*;
import java.util.stream.*;
import java.io.*;

public class ControllerImpl implements Controller {
	
	private int count = 1;
	private final String filename;
	private ObjectOutputStream oos;
	private Random rnd = new Random();
	
	public ControllerImpl(String filename){
		this.filename = filename;
	}
	
	@Override
	public boolean enableInc() {
		return this.count < 10;
	}

	@Override
	public boolean enableDec() {
		return this.count > 1;
	}
	

	
	@Override
	public void incCount() {
		this.count++;
	}

	@Override
	public void decCount() {
		this.count--;
	}

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public void hitWrite() {
		try {
			if (oos == null){
				oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			}
			this.oos.writeObject(generate(this.getCount()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Boolean> generate(int n){
		return IntStream.range(0, n).mapToObj((i)->rnd.nextBoolean()).collect(Collectors.toList());
		
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public void hitQuit() {
		try{
			this.oos.writeObject(Collections.emptyList());
			this.oos.close();
			final ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			List<Boolean> list;
			do{
				list = (List<Boolean>)ois.readObject();
				list.stream().map(b->b?"1":"0").forEach(System.out::print);
				System.out.println();
			} while (!list.isEmpty());
			ois.close();
		} catch (Exception e){
			e.printStackTrace();
		}		
	}	

}
