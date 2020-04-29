package fiber.core.server;

import com.sun.net.httpserver.HttpExchange;
import fiber.core.Fiber;
import fiber.core.Task;

import java.io.IOException;
import java.io.OutputStream;

class ServerTask implements Task {
    private final HttpExchange exchange;
    Fiber fiber;

    // to count how many ServerTasks are created
    static Integer count = 0;

    ServerTask(HttpExchange exchange) {
        this.exchange = exchange;
        this.fiber = new Fiber(this, "fiber of Task " + ++count);
    }

    @Override
    public void run(Integer pc) throws IOException {

        // Code simulates a request handler which needs to be blocked before giving
        // response to the request.
        /** ACTUAL CODE
         *  System.out.println("blocking - 1 | " + fiber.name);
         * fiber.sleep(1000)
         * System.out.println("blocking - 2 | " + fiber.name);
         * fiber.sleep(1000)
         * completeRequest()
         */

        // COMPILED CODE
        switch (pc) {
            case 1:
                System.out.println("blocking - 1 | " + fiber.name);
                // place to save the state
                fiber.sleep(1000); // suspending call
                return;
            case 2:
                System.out.println("blocking - 2 | " + fiber.name);
                // place to save the state
                fiber.sleep(1000); // suspending call
                return;
            default:
                completeRequest();
        }
    }

    private void completeRequest() throws IOException {
        String res = "end of it" + fiber.name;
        exchange.sendResponseHeaders(200, res.getBytes().length);
        OutputStream os = this.exchange.getResponseBody();
        os.write(res.getBytes());
        os.close();
    }
}

