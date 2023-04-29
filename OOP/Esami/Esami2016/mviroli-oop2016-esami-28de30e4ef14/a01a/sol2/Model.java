package ex2016.a01a.sol2;

import java.util.List;

public interface Model {
    
    List<String> availableCommands();
    
    void execCommand(String command);
    
    List<String> getAllNumbersAndReset();
}
