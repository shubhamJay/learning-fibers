package kilim.experiment.impl;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Dispatcher {
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    void dispatch(Fiber fiber) {
        executor.execute(fiber::run);
    }

    void submit(Fiber fiber) {
        executor.submit(fiber::run);
    }

    void schedule(Fiber fiber, Integer millis) {
        executor.schedule(fiber::run, millis, TimeUnit.MILLISECONDS);
    }
}