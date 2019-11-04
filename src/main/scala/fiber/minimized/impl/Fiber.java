package fiber.minimized.impl;

class Fiber {

    private Task task;
    Integer pc = 1;
    String name;

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
        Dispatcher.getCurrentDispatcher().submit(this::run);
    }

    // use this to suspend.
    void schedule(Integer millis) {
        ++pc;
        Dispatcher.getCurrentDispatcher().schedule(this::run, millis);
    }
}
