package fiber.core;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface Task {
    Fiber getFiber();

    // the user code needs to take an extra parameter of the execution counter,
    // which determines from which point to resume execution
    void run(int pc) throws IOException, ExecutionException, InterruptedException;
}
