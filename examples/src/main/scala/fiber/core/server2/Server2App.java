package fiber.core.server2;

import fiber.core.http.HttpServer;
import fiber.core.http.RouteFactory;
import fiber.core.http.RouteTask;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

public class Server2App {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer httpServer = new HttpServer(8500, new RouteFactory() {
            @Override
            public RouteTask getTask(AsynchronousSocketChannel channel) {
                return new ExampleRoutes(channel);
            }
        });
        httpServer.start();
        System.out.println("server started");

        Thread.sleep(1000000); // todo: remove this. See if deamon threads help
    }
}
