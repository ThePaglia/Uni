package ex2016.a01a.sol2;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class ModelImpl implements Model {
    
    private final static Random random = new Random();
    
    private enum Command {
        RAND((m) -> 1+random.nextInt(10)), 
        DEC((m) -> {m.currentDec--; return m.currentDec;}), 
        ZERO((m) -> 0);
        
        private Function<ModelImpl,Integer> f;
        
        private Command(Function<ModelImpl,Integer> f){
            this.f=f;
        }
        
        public int exec(ModelImpl m){
            return f.apply(m);
        }
    }
    
    private List<Integer> commands = new LinkedList<Integer>();
    private int currentDec = 0;
    private File file;
    
    public ModelImpl(String fileName){
        this.file = Objects.requireNonNull(new File(fileName));
    }

    @Override
    public List<String> availableCommands() {
        return Arrays.<Command>asList(Command.values()).stream().map(Command::name).collect(Collectors.toList());
    }

    @Override
    public void execCommand(String command) {
        int value = Command.valueOf(Command.class, command).exec(this);
        this.commands.add(value);
    }

    @Override
    public List<String> getAllNumbersAndReset() {
        List<String> ls = Collections.unmodifiableList(this.commands.stream().map(String::valueOf).collect(Collectors.toList()));
        this.writeToFile();
        this.commands.clear();
        this.currentDec = 0;
        return ls;
    }
    
    private void writeToFile(){
        try ( BufferedWriter w = new BufferedWriter(new FileWriter(this.file))){
            for (int i: this.commands){
                w.write(String.valueOf(i)); w.newLine();
            }
        } catch (IOException e){} 
    }

}
