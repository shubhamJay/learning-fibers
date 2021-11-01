package fiber.http.framework;

import fiber.core.Dispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class HttpServer {
    int port;
    RouteFactory factory;
    AsynchronousServerSocketChannel channel;

    public HttpServer(int port, RouteFactory factory) {
        this.port = port;
        this.factory = factory;
    }

    public void start() throws IOException {
        channel = AsynchronousServerSocketChannel.open();

        channel.bind(new InetSocketAddress(port));
        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                RouteTask task = factory.getTask(result);
                Dispatcher.getCurrentDispatcher().dispatch(task.getFiber());
                channel.accept(null, this);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println(exc.getMessage());
            }
        });
        Thread.currentThread().setDaemon(true);
    }

    public void stop() throws IOException {
        channel.close();
        Dispatcher.getCurrentDispatcher().shutdown();
    }
}
