package fiber.http.framework;

import fiber.core.Task;

import java.nio.channels.AsynchronousSocketChannel;

public abstract class RouteTask implements Task {
    public AsynchronousSocketChannel channel;

    public RouteTask(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }
}