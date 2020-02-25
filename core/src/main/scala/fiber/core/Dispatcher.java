package fiber.core;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
    private static Dispatcher defaultDispatcher = new Dispatcher();

    public static Dispatcher getCurrentDispatcher() {
        return defaultDispatcher;
    }

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public void dispatch(Fiber fiber) {
        executor.execute(fiber::run);
    }

    void submit(Runnable task) {
        executor.submit(task);
    }

    void schedule(Runnable task, Integer millis) {
        executor.schedule(task, millis, TimeUnit.MILLISECONDS);
    }
}