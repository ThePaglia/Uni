package ex2014.a03b.sol2;

import java.util.*;
import java.util.stream.*;

public class CodeModelImpl implements CodeModel {
	
	private static final char START = 'A';
	private static final char END = 'G';
	
	
	private List<Character> name = new ArrayList<>(Collections.nCopies(END-START+1,START));
	
	@Override
	public int size() {
		return this.name.size();
	}

	@Override
	public List<String> currentNames() {
		return name.stream().map(String::valueOf).collect(Collectors.toList());
	}

	@Override
	public List<Boolean> currentEnabling() {
		return name.stream().map((x)->x<END).collect(Collectors.toList());
	}

	@Override
	public String hit(int elem) {
		char c = this.name.get(elem);
		this.name.set(elem, ++c);
		return String.valueOf(c);
	}

	@Override
	public String result() {
		return this.name.stream().map(String::valueOf).collect(Collectors.joining(":", "(", ")"));
	}
	
	
	
}
