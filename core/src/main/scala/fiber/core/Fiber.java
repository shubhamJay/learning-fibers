package fiber.core;

public class Fiber {

    private Task task; // the task to execute on the fiber
    Integer executionCounter = 1; // place to resume execution from
    public String name;

    private Dispatcher dispatcher = Dispatcher.getCurrentDispatcher();

    public Fiber(Task task, String name) {
        this.task = task;
        this.name = name;
    }

    public void run() {
        try {
            task.run(executionCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void suspend() {
        ++executionCounter;
    }

    // use this to suspend.
    public void sleep(Integer millis) {
        ++executionCounter;
        dispatcher.schedule(this::run, millis);
    }
}
