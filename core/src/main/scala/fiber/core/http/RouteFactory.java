package fiber.core.http;

import java.nio.channels.AsynchronousSocketChannel;

public abstract class RouteFactory {
    public abstract RouteTask getTask(AsynchronousSocketChannel channel);
}
