package ex2016.a01b.sol2;

import java.io.*;
import java.util.*;

public class ModelImpl implements Model {

    private Iterator<Integer> iterator;
    
    public ModelImpl(String fileName) {
        List<Integer> list = new LinkedList<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String s;
            while ((s=br.readLine())!=null){
                list.add(Integer.parseInt(s));
            }
        } catch (Exception e){}
        this.iterator = list.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Integer next() {
        return this.iterator.next();
    }
}
