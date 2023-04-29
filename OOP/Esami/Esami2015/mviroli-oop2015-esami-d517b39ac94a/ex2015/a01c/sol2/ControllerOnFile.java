package ex2015.a01c.sol2;

import java.io.*;
import java.util.*;

public class ControllerOnFile implements Controller {
	
	private final File file;
	private final List<Integer> list = new ArrayList<>();
	private boolean inserting;
	
	public ControllerOnFile(String s) throws IOException {
		this.file = new File(s);
	}

	@Override
	public void add(int i) {
		this.inserting = true;
		this.list.add(i);
	}
	
	private void ensure(boolean b){
		if (!b){
			throw new IllegalStateException();
		}
	}

	@Override
	public void save() {
		ensure(this.inserting);
		try (PrintStream ps = new PrintStream(this.file)){
			this.list.forEach(ps::println);
		} catch (IOException e){
			throw new IllegalStateException();
		}
		this.list.clear();
		this.inserting = false;
	}

	@Override
	public void show() {
		ensure(!this.inserting);
		System.out.println("Showing..");
		try (BufferedReader br = new BufferedReader(new FileReader(this.file))){
			String s;
			while ((s = br.readLine()) != null){
				System.out.println(s);
			}
		} catch (IOException e){
			throw new IllegalStateException();
		}
	}

	@Override
	public boolean canSave() {
		return this.inserting && !this.list.isEmpty();
	}

	@Override
	public boolean canShow() {
		return !this.inserting;
	}

}
