package fiber.core;

public class Fiber {

    private Task task;
    Integer pc = 1;
    public String name;

    private Dispatcher dispatcher = Dispatcher.getCurrentDispatcher();

    public Fiber(Task task, String name) {
        this.task = task;
        this.name = name;
    }

    public void run() {
        try {
            task.run(pc);
        } catch (Exception e) {
            System.out.println("thrown exception : " + e.getMessage());
        }
    }

    public void suspend() {
        ++pc;
        dispatcher.submit(this::run);
    }

    // use this to suspend.
    public void sleep(Integer millis) {
        ++pc;
        dispatcher.schedule(this::run, millis);
    }
}
