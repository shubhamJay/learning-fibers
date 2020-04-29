package fiber.core;

import java.io.IOException;

public interface Task {

    // the user code needs to take an extra parameter of the execution counter,
    // which determines from which point to resume execution
    void run(Integer pc) throws IOException;
}
