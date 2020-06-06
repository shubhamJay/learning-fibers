package fiber.core.server;

import com.sun.net.httpserver.HttpExchange;
import fiber.core.Fiber;
import fiber.core.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

class ServerTask implements Task {
    private final HttpExchange exchange;
    private Fiber fiber;

    // to count how many ServerTasks are created
    static AtomicInteger count = new AtomicInteger(0);

    ServerTask(HttpExchange exchange) {
        this.exchange = exchange;
        fiber = new Fiber(this, "fiber of Server Task " + count.incrementAndGet());
    }

    @Override
    public Fiber getFiber() {
        return fiber;
    }


    @Override
    public void run(int pc) throws IOException {

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

