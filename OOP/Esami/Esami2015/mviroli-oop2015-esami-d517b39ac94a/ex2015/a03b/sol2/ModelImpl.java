package ex2015.a03b.sol2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModelImpl implements Model {
    
    private final List<String> list;
    private List<String> currentList;
    private final int nlines;
    private int pos = 0;
    
    
    public ModelImpl(String fileName, int nlines) throws IOException {
        this.list = Files.lines(FileSystems.getDefault().getPath(fileName)).collect(Collectors.toList());
        this.nlines = nlines;
        this.currentList = currentLines();
    }
    
    private List<String> currentLines() {
        return Collections.unmodifiableList(this.list.subList(this.pos, Math.min(this.pos + this.nlines, this.list.size())));
    } 


    @Override
    public List<String> getCurrentLines() {
        return this.currentList;
    }
    
    @Override
    public boolean hasPrev() {
        return this.pos - this.nlines >=0;
    }

    @Override
    public boolean hasNext() {
        return this.pos + this.nlines < this.list.size()-1;
    }

    @Override
    public void next() {
        this.pos = this.pos + this.nlines;
        this.currentList = currentLines();
    }
    
    @Override
    public void prev() {
        this.pos = this.pos - this.nlines;
        this.currentList = currentLines();
    }
    
}
