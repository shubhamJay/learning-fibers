package fiber.core.server2;

import fiber.core.Dispatcher;
import fiber.core.Fiber;
import fiber.core.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;


class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 3; i++) {
            Dispatcher.getCurrentDispatcher().dispatch(new ClientTask("localhost", 8500).getFiber());
        }
        Thread.sleep(100000);
    }
}


class ClientTask implements Task {

    private String host;
    private int port;
    private final AsynchronousSocketChannel channel;
    private Fiber fiber;

    public ClientTask(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.channel = AsynchronousSocketChannel.open();
        this.fiber = new Fiber(this, "client Fiber");
    }

    @Override
    public Fiber getFiber() {
        return fiber;
    }

    // todo: check how state can be managed inside the suspending function (should not need a external buffer to read)
    ByteBuffer buffer = ByteBuffer.allocate(1000);

    // todo: the completion handlers are almost same in all the places.
    @Override
    public void run(int pc) throws IOException {

        switch (pc) {
            case 1:
                channel.connect(
                        new InetSocketAddress(host, port),
                        null,
                        new CompletionHandler<Void, Void>() {
                            @Override
                            public void completed(Void result, Void attachment) {
                                Dispatcher.getCurrentDispatcher().submit(fiber::run);
                            }

                            @Override
                            public void failed(Throwable exc, Void attachment) {
                                System.out.println("failed connecting");
                            }
                        });
                fiber.suspend();
                return;

            case 2:
                channel.write(
                        ByteBuffer.wrap("hello from ".getBytes()),
                        null,
                        new CompletionHandler<Integer, Void>() {
                            @Override
                            public void completed(Integer result, Void attachment) {
                                Dispatcher.getCurrentDispatcher().submit(fiber::run);
                            }

                            @Override
                            public void failed(Throwable exc, Void attachment) {
                                System.out.println("failed writing");
                            }

                        });
                fiber.suspend();
                return;

            case 3:
                channel.read(buffer,
                        60,
                        TimeUnit.SECONDS,
                        null,
                        new CompletionHandler<Integer, Void>() {
                            @Override
                            public void completed(Integer result, Void attachment) {
                                Dispatcher.getCurrentDispatcher().submit(fiber::run);
                            }

                            @Override
                            public void failed(Throwable exc, Void attachment) {
                                System.out.println("failed reading");

                            }
                        });
                fiber.suspend();
                return;

            case 4:
                System.out.println("+" + new String(buffer.array()));
                channel.close();
        }
    }
}