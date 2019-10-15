package kilim.experiment.impl;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

interface Task {
    void run(Integer pc) throws IOException;
}

class TestTask implements Task {
    Fiber fiber;

    TestTask(Integer taskNumber) {
        this.fiber = new Fiber(this, "fiber of Task: " + taskNumber);
    }

    // Should fiber be injected or only PC.
    public void run(Integer pc) {

        System.out.println(Thread.currentThread().getName() + " ==== " + pc);

        switch (pc) {
            case 1:
                System.out.println("blocking - 1 | " + fiber.name);
                // what about state?
                // try using continuation and  state machines to resume.
                //rename to dispatcher

                fiber.schedule(1000);// could be sleep too
                return;
            case 2:
                System.out.println("blocking - 2 | " + fiber.name);
                fiber.schedule(1000);
                return;
            case 3:
                System.out.println("blocking - 3 | " + fiber.name);
                fiber.schedule(1000);
                return;
            case 4:
                System.out.println("blocking - 4 | " + fiber.name);
                fiber.schedule(1000);
                return;
            case 5:
                System.out.println("blocking - 5 | " + fiber.name);
                fiber.schedule(1000);
                return;
            default:
                System.out.println("end of it" + fiber.name);
                return;
        }
    }
}

class ServerTask implements Task {
    private final HttpExchange exchange;
    Fiber fiber;

    static Integer count = 0;

    ServerTask(HttpExchange exchange) {
        this.exchange = exchange;
        this.fiber = new Fiber(this, "fiber of Task " + ++count);
    }

    @Override
    public void run(Integer pc) throws IOException {

        switch (pc) {
            case 1:
                System.out.println("blocking - 1 | " + fiber.name);
                fiber.schedule(1000);
                return;
            case 2:
                System.out.println("blocking - 2 | " + fiber.name);
                fiber.schedule(1000);
                return;
            default:
                String res = "end of it" + fiber.name;
                exchange.sendResponseHeaders(200, res.getBytes().length);
                OutputStream os = this.exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
                return;
        }
    }

}
