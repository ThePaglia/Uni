package ex2015.a03a.sol2;

import java.util.*;

public interface Model {
    
    List<String> getQuestions();
    
    int getScore(List<Optional<Boolean>> answers);

}
