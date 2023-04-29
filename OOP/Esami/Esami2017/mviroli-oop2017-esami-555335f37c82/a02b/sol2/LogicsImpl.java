package a02b.sol2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicsImpl implements Logics {
	
	private final String fileName;
	private List<String> list;
	
	public LogicsImpl(String s) throws IOException {
		this.fileName = s;
		this.list = Collections.unmodifiableList(Files.lines(Paths.get(s)).collect(Collectors.toList()));
	}
	
	@Override
	public List<String> allLines() {
		return this.list;
	}

	@Override
	public void remove(int index) {
		this.save(this.list.stream().filter(s -> !s.equals(this.list.get(index))));
	}

	@Override
	public void add(int index) {
		this.save(this.list.stream().flatMap(s -> s.equals(this.list.get(index)) ? Stream.of(s,"*"+s): Stream.of(s)));
	}

	@Override
	public void concat(int index) {
		this.save(this.list.stream().map(s -> s.equals(this.list.get(index)) ? s+s : s));
	}
	
	private void save(Stream<String> l) {
		try (PrintStream ps = new PrintStream(fileName)){
			this.list=Collections.unmodifiableList(l.peek(ps::println).peek(System.out::println).collect(Collectors.toList()));
			System.out.println();
		} catch (IOException e){
			throw new IllegalStateException();
		}
	}
}
