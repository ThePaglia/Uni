package ex2014.a02.sol2;

import java.util.*;
import java.io.*;

/*
 * Soluzione con gli ObjectStream.. ma era possibile usare anche i DataStream o i Reader/Writer
 */

public class ControllerImpl implements Controller {
	
	private List<Button> hitList = new ArrayList<>();
	private final String filename;
	private ObjectOutputStream oos;
	
	public ControllerImpl(String filename){
		this.filename = filename;
	}
		
	@Override
	public boolean enabled(Button b) {
		return true;
	}

	@Override
	public void hit(Button b) {
		hitList.add(b);
	}

	@Override
	public boolean enabledWrite() {
		return !hitList.isEmpty();
	}
	
	@Override
	public boolean enabledQuit() {
		return hitList.isEmpty();
	}

	@Override
	public void hitWrite() {
		try {
			if (oos == null){
				oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			}
			this.oos.writeObject(hitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.hitList = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hitQuit() {
		try{
			this.hitList.clear();
			this.oos.writeObject(hitList);
			this.oos.close();
			final ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			List<Button> list;
			do{
				list = (List<Button>)ois.readObject();
				if (!list.isEmpty()){
					System.out.println(list);
				}
			} while (!list.isEmpty());
			ois.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
