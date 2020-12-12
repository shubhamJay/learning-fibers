package fiber.core.server2;

import fiber.http.framework.HttpServer;
import fiber.http.framework.RouteFactory;
import fiber.http.framework.RouteTask;

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
