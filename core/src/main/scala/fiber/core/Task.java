package fiber.core;

import java.io.IOException;

public interface Task {
    void run(Integer pc) throws IOException;
}

