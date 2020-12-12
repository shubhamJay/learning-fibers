package fiber.core;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
    private static Dispatcher defaultDispatcher = new Dispatcher();

    public static Dispatcher getCurrentDispatcher() {
        return defaultDispatcher;
    }

    // Threads are the underlying Concurrency primitive on which fibers are scheduled.
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public void dispatch(Fiber fiber) {
        executor.execute(fiber::run);
    }

    public void submit(Runnable task) {
        executor.submit(task);
    }

    void schedule(Runnable task, Integer millis) {
        executor.schedule(task, millis, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}