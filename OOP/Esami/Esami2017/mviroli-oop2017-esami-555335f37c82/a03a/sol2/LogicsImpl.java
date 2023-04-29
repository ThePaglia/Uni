package a03a.sol2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {
	
	private final String fileName;
	private final List<String> initialList;
	private final List<String> workingList;
	
	public LogicsImpl(String s) throws IOException {
		this.fileName = s;
		this.initialList = Files.lines(Paths.get(s)).collect(Collectors.toList());
		this.workingList = new ArrayList<>(this.initialList);
	}
	
	@Override
	public List<String> allLines() {
		return initialList;
	}

	@Override
	public void disable(int index) {
		this.workingList.set(index, null);
	}

	@Override
	public void closeAndWrite() {
		try (PrintStream ps = new PrintStream(fileName)){
			this.workingList.stream().filter(s->s!=null).peek(ps::println).forEach(System.out::println);
		} catch (IOException e){
			throw new IllegalStateException();
		}
	}
}
