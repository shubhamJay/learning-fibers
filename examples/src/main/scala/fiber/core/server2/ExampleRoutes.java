package fiber.core.server2;

import fiber.core.Dispatcher;
import fiber.core.Fiber;
import fiber.http.framework.RouteTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.atomic.AtomicInteger;

// Fixme: these are not routes. Routes come in to picture once request is read. so step 2 with a path check is a route.
public class ExampleRoutes extends RouteTask {
    Fiber fiber;
    static AtomicInteger count = new AtomicInteger(0);

    public ExampleRoutes(AsynchronousSocketChannel channel) {
        super(channel);
        fiber = new Fiber(this, "fiber: " + count.incrementAndGet());
    }

    @Override
    public Fiber getFiber() {
        return fiber;
    }


    ByteBuffer request = ByteBuffer.allocate(1000); // request content

    @Override
    public void run(int pc) throws IOException {
        switch (pc) {
            case 1:
                channel.read(request, null, new CompletionHandler<>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        Dispatcher.getCurrentDispatcher().submit(fiber::run);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("failed reading");
                    }
                });
                System.out.println(fiber.name + " suspension number 1");
                fiber.suspend();
                return;

            case 2:
                // consume bytes and perform sever actions
                System.out.println(fiber.name + new String(request.array()));

                channel.write(ByteBuffer.wrap("Hello from server ".repeat(100).getBytes()), null, new CompletionHandler<>() {

                    @Override
                    public void completed(Integer result, Object attachment) {
                        Dispatcher.getCurrentDispatcher().submit(fiber::run);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("failed writing");
                    }
                });
                fiber.suspend();
                return;
            case 3:
                channel.close();
        }
    }


/*

public void run(int pc) throws IOException {
    String request = channel.readRequest();
    System.out.println(fiber.name + new String(request.array()));
    channel.write("Hello from server")
    channel.close()
}
 */
}

