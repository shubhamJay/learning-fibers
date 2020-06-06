package fiber.core.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fiber.core.Dispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerApp {

    static AtomicInteger count = new AtomicInteger(0);

    private static Dispatcher dispatcher = Dispatcher.getCurrentDispatcher();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(ServerApp::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {

        ServerTask serverTask = new ServerTask(exchange);
        dispatcher.dispatch(serverTask.getFiber());  // schedules the task (fiber for execution)
        System.out.println("request scheduled " + count.addAndGet(1));
    }
}
