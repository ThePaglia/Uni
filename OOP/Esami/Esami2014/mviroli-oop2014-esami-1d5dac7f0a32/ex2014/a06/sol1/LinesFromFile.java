package ex2014.a06.sol1;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;


public class LinesFromFile implements Lines {
	
	private List<String> ls = null;
	private int pos = 0;
	
	public LinesFromFile(String fileName) throws IOException {
		this.ls = Files.lines(FileSystems.getDefault().getPath(fileName)).collect(Collectors.toList());
	}

	@Override
	public boolean isEmpty() {
		return this.ls.isEmpty();
	}

	@Override
	public boolean isTop() {
		return this.pos == 0;
	}

	@Override
	public boolean isBottom() {
		return this.pos == this.ls.size()-1;
	}

	@Override
	public Optional<String> getCurrentString() {
		return this.pos < this.ls.size() ? Optional.of(this.ls.get(this.pos)) : Optional.empty();
	}

	@Override
	public void goUp() {
		this.pos--;		
	}

	@Override
	public void goDown() {
		this.pos++;				
	}

}
