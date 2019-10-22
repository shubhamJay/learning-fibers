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
        Runner.dispatcher.submit(this);
    }

    // use this to suspend.
    void schedule(Integer millis) {
        ++pc;
        Runner.dispatcher.schedule(this, millis);
    }
}
