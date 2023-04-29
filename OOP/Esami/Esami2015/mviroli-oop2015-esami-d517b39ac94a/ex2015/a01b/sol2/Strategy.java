package ex2015.a01b.sol2;

import java.util.List;

/**
 *
 */
public interface Strategy {

    /**
     * @param command
     *            the command to execute
     * @return the result of the operation
     */
    String doOperation(String command);

    /**
     * @return the name of the possible inputs
     */
    List<String> getInputs();

    /**
     * @return the available operations
     */
    List<String> getOperations();

    /**
     * If this model has a mutable status, reset it to default.
     */
    void reset();

    /**
     * @param in
     *            sends an input information to this strategy
     */
    void hitString(String in);

}
