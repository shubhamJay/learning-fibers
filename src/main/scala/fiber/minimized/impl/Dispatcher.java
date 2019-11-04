package fiber.minimized.impl;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Dispatcher {
    private static Dispatcher dispatcher = new Dispatcher();

    static Dispatcher getCurrentDispatcher() {
        return dispatcher;
    }

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    void dispatch(Fiber fiber) {
        executor.execute(fiber::run);
    }

    void submit(Runnable task) {
        executor.submit(task);
    }

    void schedule(Runnable task, Integer millis) {
        executor.schedule(task, millis, TimeUnit.MILLISECONDS);
    }
}