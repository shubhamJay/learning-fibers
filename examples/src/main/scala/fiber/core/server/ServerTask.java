package fiber.core.server;

import com.sun.net.httpserver.HttpExchange;
import fiber.core.Fiber;
import fiber.core.Task;

import java.io.IOException;
import java.io.OutputStream;

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

        /**
         *  System.out.println("blocking - 1 | " + fiber.name);
         * Thread.sleep(1000)
         * System.out.println("blocking - 2 | " + fiber.name);
         * Thread.sleep(1000)
         * completeRequest()
         */

        switch (pc) {
            case 1:
                System.out.println("blocking - 1 | " + fiber.name);
                fiber.sleep(1000); // Thread.sleep
                return;
            case 2:
                System.out.println("blocking - 2 | " + fiber.name);
                fiber.sleep(1000); // Thread.sleep
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

