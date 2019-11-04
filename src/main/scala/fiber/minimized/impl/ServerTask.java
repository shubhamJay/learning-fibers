package fiber.minimized.impl;

import com.sun.net.httpserver.HttpExchange;

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

