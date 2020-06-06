package fiber.core.server2;

import fiber.core.Dispatcher;
import fiber.core.Fiber;
import fiber.core.http.RouteTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.atomic.AtomicInteger;

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

    ByteBuffer byteBuffer = ByteBuffer.allocate(1000); // request content
    String[] requestBody = new String[1]; // todo: fix this;

    @Override
    public void run(int pc) throws IOException {

        switch (pc) {
            case 1:
                channel.read(byteBuffer, null, new CompletionHandler<>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println(fiber.name + " completed reading ");
                        requestBody[0] = new String(byteBuffer.array());
                        Dispatcher.getCurrentDispatcher().submit(fiber::run);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                System.out.println(fiber.name + " suspending 1");
                fiber.suspend();
                return;

            case 2:
                System.out.println(fiber.name + requestBody[0]);

                channel.write(ByteBuffer.wrap("Hello".getBytes()), null, new CompletionHandler<>() {

                    @Override
                    public void completed(Integer result, Object attachment) {
                        Dispatcher.getCurrentDispatcher().submit(fiber::run);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                fiber.suspend();
                return;
            case 3:
                channel.close();
        }
    }
}

