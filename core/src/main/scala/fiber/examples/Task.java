package fiber.examples;

import java.io.IOException;

interface Task {
    void run(Integer pc) throws IOException;
}

