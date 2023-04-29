package ex2015.a03a.sol2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelImpl implements Model {
    
    private List<Pair<String,Boolean>> list;
    
    public ModelImpl(String fileName) throws IOException {
        this.list = Files.lines(FileSystems.getDefault().getPath(fileName))
                         .map(str -> new Pair<>(str.substring(3),str.substring(0,3).equals("Y, ")))
                         .collect(Collectors.toList());
    }
    

    @Override
    public List<String> getQuestions() {
        return this.list.stream().map(Pair::getX).collect(Collectors.toList());
    }

    @Override
    public int getScore(List<Optional<Boolean>> answers) {
        System.out.println(answers);
        int i = 0;
        int ct = 0;
        for (Optional<Boolean> opt: answers){
            ct += !opt.isPresent() ? 0 : opt.get() == list.get(i).getY() ? 1 : -1;
            i++;
        }
        return ct;
    }

}
